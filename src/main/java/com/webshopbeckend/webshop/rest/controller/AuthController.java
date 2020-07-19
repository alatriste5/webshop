package com.webshopbeckend.webshop.rest.controller;

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
    public Response authenticateUser(User user)  {
        try {
            LoggedInUser aU = authService.Login(user.getUsername(), user.getPassword());

            System.out.println("Lista db: " + authService.getActiveUserListSize());
            String token = authService.activeUserList.get(authService.getActiveUserListSize()-1).getToken();
            System.out.println("Last User token: " + token);

            return Response.ok(aU).build();

        } catch (Exception e) {
            System.out.println("AuthController error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

