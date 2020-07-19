package com.webshopbeckend.webshop.rest.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggedInUser extends User{
    String token;
    Date registeredIn;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public LoggedInUser(int id, String username, String name, String email, int addressId, String role, Date createdat, String token) {
        super(id, username, name, email, addressId, role, createdat);
        this.token = token;
        this.registeredIn = new Date();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getRegisteredIn() {
        return registeredIn;
    }
    public String getRegisteredIn2() {
        return formatter.format(registeredIn);
    }

    public void setRegisteredIn(Date registeredIn) {
        this.registeredIn = registeredIn;
    }
}
