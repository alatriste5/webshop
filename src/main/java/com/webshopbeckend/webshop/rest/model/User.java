package com.webshopbeckend.webshop.rest.model;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String name;
    private String password;
    private String email;
    private int addressid;
    private String role;
    private Date createdat;

    public User(){

    }

    public User(int id, String username, String name, String password,
                String email, int addressid, String role, Date createdat) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.addressid = addressid;
        this.role = role;
        this.createdat = createdat;
    }

    public User(int id, String username, String name,
                String email, int addressid, String role, Date createdat) {
        this(id,username,name,null,email,addressid,role,createdat);
    }

    public User(String username){
        this.username = username;
    }


    public Date getcreatedat() {
        return createdat;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
