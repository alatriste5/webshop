package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.Product;
import com.webshopbeckend.webshop.rest.services.AuthService;
import com.webshopbeckend.webshop.rest.services.ProductServiceImpl;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@ApplicationScoped
@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {

    private ProductServiceImpl productService;
    private AuthService authService;

    public ProductController(){
    }

    @Inject
    public ProductController(ProductServiceImpl productServiceImpl){
        this.productService = productServiceImpl;
        this.authService = new AuthService();
    }

    @GET
    @Path("/valid")
    public Response findAllValidProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValid(token)){
            Collection<Product> resCol = this.productService.findAllProductByValid(1);
            if(resCol != null) {
                return Response.status(Response.Status.OK).entity(resCol).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Products not found").build();
            }
        }
        else {
            System.out.println("ProductController error - findAllValidProduct called with wrong token: "+token);
            return Response.status(Response.Status.BAD_REQUEST).entity("You are not allowed to get this data").build();
        }
    }

    @GET
    @Path("/unvalid")
    public Response findAllUnValidProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValidAndAdmin(token)){
            Collection<Product> resCol = this.productService.findAllProductByValid(0);
            if(resCol != null) {
                return Response.status(Response.Status.OK).entity(resCol).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Products not found").build();
            }
        }
        else{
            System.out.println("ProductController error - findAllUnValidProduct called with wrong token: "+token);
            return Response.status(Response.Status.BAD_REQUEST).entity("You are not allowed to get this data").build();
        }
    }

    @GET
    @Path("/sold")
    public Response findAllSoldProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValid(token)){
            Collection<Product> resCol =  this.productService.findAllProductByValid(2);
            if(resCol != null) {
                return Response.status(Response.Status.OK).entity(resCol).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Products not found").build();
            }
        }
        else{
            System.out.println("ProductController error - findAllSoldProduct called with wrong token: "+token);
            return Response.status(Response.Status.BAD_REQUEST).entity("You are not allowed to get this data").build();
        }
    }
    @GET
    @Path("/purchases/{id}")
    public Response findAllProductByCustomer(@PathParam("id") int id, @DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValid(token)){
            Collection<Product> resCol =  this.productService.findAllProductByCustomeriId(id);
            if(resCol != null) {
                return Response.status(Response.Status.OK).entity(resCol).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Products not found").build();
            }
        }
        else{
            System.out.println("ProductController error - findAllProductByCustomer called with wrong token: "+token);
            return Response.status(Response.Status.BAD_REQUEST).entity("You are not allowed to get this data").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id, @DefaultValue("") @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValid(token)){
            Product resProd = this.productService.findById(id);
            if(resProd != null){
                return Response.status(Response.Status.OK).entity(resProd).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Product not found").build();
            }
        }
        else{
            System.out.println("ProductController error - findById called with wrong token: "+token);
            //return null;
            return Response.status(Response.Status.BAD_REQUEST).entity("You are not allowed to get this data").build();
        }
    }

    //Set product to valid
    @PUT
    @Path("/valid/{id}")
    public boolean updateProductValid(@PathParam("id") int id, @DefaultValue("") @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValidAndAdmin(token)){
            return this.productService.setProductValid(id);
        } else {
            System.out.println("ProductController error - updateProductValid called with wrong token: "+token);
            return false;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addProduct(Product prod, @QueryParam("auth") String token) {
        System.out.println("Controller addProd");
        if(this.authService.checkTokenIsValid(token)) {
            System.out.println("token ok");
            return productService.addProduct(prod);
        } else {
            System.out.println("ProductController error - addProduct called with wrong token: "+token);
            return false;
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product prod, @QueryParam("auth") String token) {
        try {
            if(this.authService.checkTokenIsBelongToSellerOrAdmin(token,prod)){
                this.productService.updateProduct(prod);

                return Response.status(200,"ok").build();
            }
        } catch (Exception e) {
            System.out.println("ProductController error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return null;
    }

    @DELETE
    @Path("/delete/{id}")
    public boolean deleteProduct(@PathParam("id") int id, @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValid(token)) {
            return this.productService.deleteById(id);
        } else {
            System.out.println("ProductController error - deleteProduct called with wrong token: "+token);
            return false;
        }
    }

    @GET
    @Path("/ownvalid/{sellerid}")
    public Collection<Product> findProductsBySeller(@PathParam("sellerid") int sellerid, @DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValid(token)) {
            return this.productService.findProductsBySeller(sellerid);
        }
        else{
            System.out.println("ProductController error - findProductsBySellerAndValidAnd called with wrong token: "+token);
            return null;
        }
    }

    @PUT
    @Path("/sell")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean sellProduct(Product prod, @QueryParam("auth") String token) {
        try {
            if(this.authService.checkTokenIsValid(token)){
                this.productService.sellProduct(prod.getId(),prod.getCustomerid());

                return true;
            }
        } catch (Exception e) {
            System.out.println("ProductController error: "+ e.getMessage());
        }
        return false;
    }

}
