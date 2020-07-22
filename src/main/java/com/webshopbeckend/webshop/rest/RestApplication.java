package com.webshopbeckend.webshop.rest;

import com.webshopbeckend.webshop.rest.model.LoggedInUser;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@ApplicationPath("/")
public class RestApplication extends Application {
    public static Connection con;
    public static ArrayList<LoggedInUser> activeUserList;

    public RestApplication() {
        activeUserList = new ArrayList<>();
        con = getConnection();
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
}
