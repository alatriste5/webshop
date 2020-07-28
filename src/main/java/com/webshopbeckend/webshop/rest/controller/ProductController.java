package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.Product;
import com.webshopbeckend.webshop.rest.services.AuthService;
import com.webshopbeckend.webshop.rest.services.ProductService;
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

    private String token;
    private ProductServiceImpl productService;
    private AuthService authService;

    public ProductController(){
    }

    @Inject
    public ProductController(ProductServiceImpl productServiceImpl){
        this.productService = productServiceImpl;
        this.authService = new AuthService();
    }

    //Not used yet
    /*@GET
    public Collection<Product> findAllProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValidAndAdmin(token)){
            return this.productService.findAllProduct();
        }
        else{
            System.out.println("ProductController error - findAllProduct called with wrong token: "+token);
            return null;
        }
    }*/

    @GET
    @Path("/valid")
    public Collection<Product> findAllValidProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValid(token)){
            return this.productService.findAllProductByValid(1);
        }
        else {
            System.out.println("ProductController error - findAllValidProduct called with wrong token: "+token);
            return null;
        }
    }

    @GET
    @Path("/unvalid")
    public Collection<Product> findAllUnValidProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValidAndAdmin(token)){
            return this.productService.findAllProductByValid(0);
        }
        else{
            System.out.println("ProductController error - findAllUnValidProduct called with wrong token: "+token);
            return null;
        }
    }

    @GET
    @Path("/sold")
    public Collection<Product> findAllSoldProduct(@DefaultValue("") @QueryParam("auth") String token){
        if(this.authService.checkTokenIsValid(token)){
            return this.productService.findAllProductByValid(2);
        }
        else{
            System.out.println("ProductController error - findAllSoldProduct called with wrong token: "+token);
            return null;
        }
    }
    @GET
    @Path("/purchases/{id}")
    public Collection<Product> findAllProductByCustomer(@PathParam("id") int id, @DefaultValue("") @QueryParam("auth") String token){

        if(this.authService.checkTokenIsValid(token)){
            return this.productService.findAllProductByCustomeriId(id);
        }
        else{
            System.out.println("ProductController error - findAllProductByCustomer called with wrong token: "+token);
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public Product findById(@PathParam("id") int id, @DefaultValue("") @QueryParam("auth") String token) {
        if(this.authService.checkTokenIsValid(token)){
            return this.productService.findById(id);
        }
        else{
            System.out.println("ProductController error - findById called with wrong token: "+token);
            return null;
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
        if(this.authService.checkTokenIsValid(token)) {
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

                return Response.status(200,"lofasz").build();
            }
        } catch (Exception e) {
            System.out.println("ProductController error: "+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return null;
    }
/*
    @GET
    @Path("/count")
    public int count(){
        return this.productService.productCount();
    }
*/
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
