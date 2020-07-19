package com.webshopbeckend.webshop.rest;


import com.webshopbeckend.webshop.rest.services.encryption.Decoder;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Calendar;


@Path("/hello")
public class HelloWorldEndpoint {
    @GET
    @Produces("text/plain")
    public Response doGet() {

//        Calendar calendar =  Calendar.getInstance();
//        long most = calendar.getTime().getTime();

        return Response.ok("Hello from Thorntail!  "/* +
                most*/).build();
    }
}
