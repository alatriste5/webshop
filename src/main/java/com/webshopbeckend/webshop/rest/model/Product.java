package com.webshopbeckend.webshop.rest.model;

import java.util.Date;

public class Product {
    int id;
    String name;
    int price;
    String details;
    int sellerid;
    int customerid;
    private Date soldat;
    int valid;
    String image;


    public Product(){

    }

    public Product(int id, String name, int price, String details, int sellerid, int customerid, Date soldat, int valid, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.details = details;
        this.sellerid = sellerid;
        this.customerid = customerid;
        this.soldat = soldat;
        this.valid = valid;
        this.image = image;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getSoldat() {
        return soldat;
    }

    public void setSoldat(Date soldat) {
        this.soldat = soldat;
    }
}
