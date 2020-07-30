package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.LoggedInUser;
import com.webshopbeckend.webshop.rest.model.User;
import com.webshopbeckend.webshop.rest.services.AddressServiceImpl;
import com.webshopbeckend.webshop.rest.services.AuthService;
import com.webshopbeckend.webshop.rest.services.UserServiceImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    AuthService authService;
    UserServiceImpl userService;
    AddressServiceImpl addressService;

    @Inject
    public AuthController(){
        authService = new AuthService();
        userService = new UserServiceImpl();
        addressService = new AddressServiceImpl();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user)  { //There is not need to check the token.
        try {
            LoggedInUser aU = authService.Login(user.getUsername(), user.getPassword());

            String token = RestApplication.activeUserList.get(authService.getActiveUserListSize()-1).getToken();

            System.out.println("Last logged in username: "+RestApplication.activeUserList.get(RestApplication.activeUserList.size()-1).getUsername() +
                    "Active users db: "+RestApplication.activeUserList.size());

                return Response.ok(aU).build();

        } catch (Exception e) {
            System.out.println("AuthController error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/logout")
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response logoutUser(@QueryParam("auth") String token){
        System.out.println("Logout called");
        try {
            boolean resp = authService.Logout(token);
            return Response.ok(resp).build();
        } catch (Exception e) {
            System.out.println("AuthController error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

