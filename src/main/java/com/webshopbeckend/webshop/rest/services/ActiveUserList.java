package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.model.LoggedInUser;

import java.util.ArrayList;

//Singleton pattern
public class ActiveUserList {

    private static ActiveUserList instance;
    private static ArrayList<LoggedInUser> activeUserList; //Store the active user in this list


    private ActiveUserList(){}

    public static ActiveUserList getInstance(){
        if (instance == null){
            instance = new ActiveUserList();
            activeUserList = new ArrayList<>();
        }
        return instance;
    }

    public static ArrayList<LoggedInUser> getActiveUserList() {
        return activeUserList;
    }
}
