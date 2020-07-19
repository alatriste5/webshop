package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.LoggedInUser;
import com.webshopbeckend.webshop.rest.model.SecretKey;
import com.webshopbeckend.webshop.rest.model.User;
import com.webshopbeckend.webshop.rest.services.encryption.Decoder;
import com.webshopbeckend.webshop.rest.services.tokengenerate.RandomString;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class AuthService {

    public static String secretKey = SecretKey.getSecretKey();
    public ArrayList<LoggedInUser> activeUserList; //TODO: Kivenni a publicot
    RandomString randomString;
    boolean newUserIsAlreadLoggedIn;
    UserServiceImpl userService;

    public AuthService(){
        activeUserList = new ArrayList<>();
        randomString = new RandomString();
        userService = new UserServiceImpl();
    }

    public LoggedInUser Login(String username, String password) throws Exception {
        try {
            //System.out.println("Username got: "+username);
            //System.out.println("Password got: "+password);

            newUserIsAlreadLoggedIn = false;
            if (RestApplication.con != null) {
                String encryptedPassword = Decoder.encrypt(password,secretKey);
                String sql = "SELECT * FROM user WHERE user.username = '" + username + "' AND user.password = '" +
                       encryptedPassword +"';";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("addressId"),
                            rs.getString("role"),
                            rs.getDate("createdat")
                    );

                    String token = randomString.nextString();
                    LoggedInUser justLoggedInUser = new LoggedInUser(
                            user.getId(),
                            user.getUsername(),
                            user.getName(),
                            user.getEmail(),
                            user.getAddressid(),
                            user.getRole(),
                            user.getcreatedat(),
                            token
                    );

                    //Check the new User can login or he is already logged in the system
                    CheckUserCanlLogin(justLoggedInUser);

                    if(newUserIsAlreadLoggedIn){
                        throw new Exception("User already logged in");
                    } else {
                        activeUserList.add(justLoggedInUser);
                        return justLoggedInUser;
                    }
                }
                //Error if username or password not match
                throw new Exception("Username or password wasn't correct!");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Auth Service-Login error: "+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    private void CheckUserCanlLogin(LoggedInUser justLoggedInUser){
        if(activeUserList.size() > 0){
            activeUserList.forEach((i) -> validateUserIsLoggedIn(i,justLoggedInUser));
        }
    }
    private void validateUserIsLoggedIn(LoggedInUser alreadyLoggedIn, LoggedInUser tryToLogin){
        if (alreadyLoggedIn.getId() == tryToLogin.getId()){
            newUserIsAlreadLoggedIn = true; //The tryToLogin User already logged in theActiveUser alreadyLoggedIn system, denie the try to login again
        }
    }

    public int getActiveUserListSize(){
        return activeUserList.size();
    }

}
