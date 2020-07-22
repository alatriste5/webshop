package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.Address;
import com.webshopbeckend.webshop.rest.services.AddressService;
import com.webshopbeckend.webshop.rest.services.AuthService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/address")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressController {

    private AddressService addressService;
    private AuthService authService;

    public AddressController(){

    }

    @Inject
    public AddressController(AddressService addressService){
        this.addressService = addressService;
        this.authService = new AuthService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAddress(Address address) { //There is not need to check the token.
        try {
            int addressid = addressService.createAddress(address);

            return Response.ok(addressid).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Address findById(@PathParam("id") int id, @DefaultValue("") @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValid(token)) {
            return this.addressService.findById(id);
        } else {
            System.out.println("AddressController error - findById called with wrong token: "+token);
        }
        return null;
    }

    @DELETE
    @Path("/delete/{id}")
    public boolean deleteAddress(@PathParam("id") int id, @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValid(token)) {
            return this.addressService.deleteAddress(id);
        } else {
            System.out.println("AddressController error - deleteAddress called with wrong token: "+token);
        }
        return false;
    }

    @POST
    @Path("/update")
    public boolean updateAddress(Address address, @QueryParam("auth") String token) {
        try {
            if(this.authService.checkTokenIsValid(token)) {

                return addressService.updateAddress(address);
            } else {
                System.out.println("AddressController error - updateAddress called with wrong token: "+token);
            }
        }
        catch (Exception e){
            System.out.println("AddressController: " + e.getMessage());
        }
        return false;
    }

}
