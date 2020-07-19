package com.webshopbeckend.webshop.rest.model;

public class Address {
    int id;
    String country;
    int postcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    String city;
    String street;
    int house;

    public Address(){

    }

    public Address(int id, String country, int postcode, String city, String street, int house) {
        this.id = id;
        this.country = country;
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }
}


