package com.webshopbeckend.webshop.rest.model;

public class SecretKey {
    static private String secretKey = "ssshhhhhhhhhhh!!!!";

    static public String getSecretKey() {
        return secretKey;
    }
}
