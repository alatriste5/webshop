package com.webshopbeckend.webshop.rest;

import com.webshopbeckend.webshop.rest.model.LoggedInUser;
import io.undertow.util.DateUtils;
import sun.rmi.runtime.Log;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@ApplicationPath("/")
public class RestApplication extends Application {
    public static Connection con;
    public static ArrayList<LoggedInUser> activeUserList;

    public RestApplication() {
        activeUserList = new ArrayList<>();
        con = getConnection();

        /*I wrote this method to make sure that every user will be logged out after a couple of minutes.
        The frontend has an own auto logged out method, and that logged out the user after 10 minutes,
        but if any problems appear on that side the current logged in users never will be logged out.
        This method handle this problem.
        Every time the active user reach an endpoint i refresh the registeredIn field. If the user do not
        make any acivity after 10 minutes this function logged out the inactive user from the activeUserList.
        With this we can make sure that our main list, the activeUserList only contains the active users.
         */
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                autoLogout();
            }
        },0,60000); //Run this check in every minute
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/webshop?serverTimezone=UTC";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void autoLogout(){
        long currdate = new Date().getTime();
        int beforenumber = activeUserList.size();
        for(int i = 0; i < activeUserList.size(); i++){
            long regIn = activeUserList.get(i).getRegisteredIn().getTime()+600000; //The user will be logged out if the user inactive in the last 10 minutes.
            if(currdate > regIn){
                System.out.println("Auto logout: "+activeUserList.get(i).getUsername()+ " was too long inactive");
                activeUserList.remove(i);
            }
        }
        System.out.println("Autologgout went through. The result: There was " + beforenumber+ " active user. Removed: " + (beforenumber-activeUserList.size()) + ". Current active user: "+ activeUserList.size());
    }



}
