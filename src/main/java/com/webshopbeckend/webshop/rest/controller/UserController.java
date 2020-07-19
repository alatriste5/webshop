package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.User;
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

    public UserController(){

    }

    @Inject
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GET
    public Collection<User> findAllUser(){
        return this.userService.findAllUser();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        try {
            boolean added = userService.addUser(user);

            return Response.ok(added).build();
        } catch (Exception e) {
            System.out.println("UserController" + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/update")
    public Response updateUser(User user) {
        try {
            boolean success = userService.updateUser(user);

            if(success) {
                return Response.ok(success).build();
            }
        } catch (Exception e) {
            System.out.println("UserController error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return null;
    }

    @POST
    @Path("/updatebyusername")
    public boolean updateUserByUsername(User user) {
        if (userService.updateUserAddressId(user)){
            return true;
        }
        return false;
    }

    @GET
    @Path("/count")
    public int count(){
        return this.userService.userCount();
    }

    @GET
    @Path("/{id  }")
    public User findById(@PathParam("id") int id) {
        return this.userService.findById(id);
    }

    @GET
    @Path("/username/{id}")
    public User getusername(@PathParam("id") int id) {
        return this.userService.getUsername(id);
    }

    @DELETE
    @Path("/delete/{id}")
    public boolean deleteUser(@PathParam("id") int id) {
        return this.userService.deleteById(id);
    }


}
