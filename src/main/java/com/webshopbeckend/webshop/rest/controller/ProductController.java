package com.webshopbeckend.webshop.rest.controller;

import com.webshopbeckend.webshop.rest.model.Product;
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

    private ProductServiceImpl productService;

    public ProductController(){
    }

    @Inject
    public ProductController(ProductServiceImpl productServiceImpl){
        this.productService = productServiceImpl;
    }

    @GET
    public Collection<Product> findAllProduct(){

        return this.productService.findAllProduct();
    }

    @GET
    @Path("/valid")
    public Collection<Product> findAllValidProduct(){
        return this.productService.findAllValidProduct();
    }

    @GET
    @Path("/{id}")
    public Product findById(@PathParam("id") int id) {
        return this.productService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addProduct(Product prod) {
        return productService.addProduct(prod);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(Product prod) {
        productService.updateProduct(prod);
    }

    @GET
    @Path("/count")
    public int count(){
        return this.productService.productCount();
    }

    @DELETE
    @Path("/delete/{id}")
    public boolean deleteUser(@PathParam("id") int id) {
        return this.productService.deleteById(id);
    }


/*
    @GET
    @Path("imagetest")
    public Response getImage2() {
        return this.productService.ShowImagetest2();
    }
*/

    //001 probálkozá
    /*Ez a produces-el postmenben már képként jelenítette meg, nélküle karakterek
    //@Produces("image/png")
    @GET
    @Path("imagetest")
    public Response getImage() {
        return this.productService.ShowImagetest();
    }
*/
}
