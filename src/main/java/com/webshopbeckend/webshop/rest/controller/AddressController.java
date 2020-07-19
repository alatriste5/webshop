package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.Address;
import com.webshopbeckend.webshop.rest.model.User;
import com.webshopbeckend.webshop.rest.services.AddressService;
import com.webshopbeckend.webshop.rest.services.AddressServiceImpl;
import com.webshopbeckend.webshop.rest.services.UserService;
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

    public AddressController(){

    }

    @Inject
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAddress(Address address) {
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
    public Address findById(@PathParam("id") int id) {
        return this.addressService.findById(id);
    }

    @DELETE
    @Path("/delete/{id}")
    public boolean deleteAddress(@PathParam("id") int id) {
        return this.addressService.deleteAddress(id);
    }

    @POST
    @Path("/update")
    public boolean updateAddress(Address address) {
        try {
            return addressService.updateAddress(address);
        }
        catch (Exception e){
            System.out.println("AddressController: " + e.getMessage());
        }
        return false;
    }

}
