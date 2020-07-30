package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.User;
import com.webshopbeckend.webshop.rest.services.AuthService;
import com.webshopbeckend.webshop.rest.services.UserService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@ApplicationScoped
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService;
    private AuthService authService;

    public UserController(){

    }

    @Inject
    public UserController(UserService userService){
        this.userService = userService;
        this.authService = new AuthService();
    }

    @GET
    public Collection<User> findAllUser(@QueryParam("auth") String token){
        if(this.authService.checkTokenIsValidAndAdmin(token)) {
            return this.userService.findAllUser();
        }
        else{
            System.out.println("UserController error - findAllUser called with wrong token: "+token);
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) { //There is not need to check the token.
        try {
            String newUserName = userService.addUser(user);
            System.out.println("New user signed up: " + newUserName);
            return Response.status(200,newUserName).build();
        } catch (Exception e) {
            System.out.println("UserController" + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/update")
    public Response updateUser(User user, @QueryParam("auth") String token) {
        try {
            boolean isadmin = this.authService.checkTokenIsValidAndAdmin(token);
            if(isadmin || this.authService.checkTokenIsValid(token)) {
                boolean success = userService.updateUser(user, isadmin);
                if(success) {
                    return Response.ok(success).build();
                }
            } else {
                System.out.println("UserController error - updateUser called with wrong token: "+token);
                return Response.status(Response.Status.BAD_REQUEST).entity("Wrong token").build();
            }
        } catch (Exception e) {
            System.out.println("UserController - updateUser error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return null;
    }

    @POST
    @Path("/updatebyusername") //There is not need a token -> this called just after the user create first address
    public boolean updateUserByUsername(User user) {
        System.out.println("updateUserByUsername called with " + user.getUsername());
            if (userService.updateUserAddressId(user)){
                return true;
            } else {
                System.out.println("UserController error - updateUserByUsername");
            }
        return false;
    }

    @GET
    @Path("/{id}")
    public User findById(@PathParam("id") int id, @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValidAndAdminOrOwn(token, id)) {
            return this.userService.findById(id);
        }
        else{
            System.out.println("UserController error - findById called with wrong token: "+token);
            return null;
        }
    }

    @GET
    @Path("/username/{id}")
    public User getusername(@PathParam("id") int id, @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValid(token)) {
            return this.userService.getUsername(id);
        }
        else{
            System.out.println("UserController error - getusername called with wrong token: "+token);
            return null;
        }
    }

    @POST
    @Path("/delete")
    public Response deleteUser(User user, @QueryParam("auth") String token) {
        try {
        String check = this.authService.checkTokenIsValidAndAdmin2(token, user.getPassword());
            if(check == "ok") {
                boolean success = this.userService.deleteById(user.getId());
                if (success){
                    System.out.println("User deleted: " + user.getUsername());
                    return Response.ok(success).build();
                }
            } else {
                System.out.println("UserController error: "+check);
                return Response.status(Response.Status.BAD_REQUEST).entity(check).build();
            }
        } catch (Exception e) {
            System.out.println("UserController - deleteUser error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return null;
    }

    @POST
    @Path("/updateasadmin")
    public Response updateUserAsAdmin(User user, @QueryParam("auth") String token) {
        try {

            String check = this.authService.checkTokenIsValidAndAdmin2(token, user.getPassword());

            if(check == "ok") {
                boolean success = userService.updateUserAsAdmin(user);
                if(success) {
                    return Response.ok(success).build();
                }
            } else {
                System.out.println("UserController error: "+check);
                return Response.status(Response.Status.BAD_REQUEST).entity(check).build();
            }
        } catch (Exception e) {
            System.out.println("UserController - updateUserAsAdmin error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return null;
    }

}
