package com.webshopbeckend.webshop.rest.services;

import com.sun.org.apache.bcel.internal.generic.RET;
import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.LoggedInUser;
import com.webshopbeckend.webshop.rest.model.Product;
import com.webshopbeckend.webshop.rest.model.SecretKey;
import com.webshopbeckend.webshop.rest.model.User;
import com.webshopbeckend.webshop.rest.services.encryption.Decoder;
import com.webshopbeckend.webshop.rest.services.tokengenerate.RandomString;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class AuthService {

    public static String secretKey = SecretKey.getSecretKey();
    RandomString randomString;
    boolean newUserIsAlreadLoggedIn;
    UserServiceImpl userService;
    ProductServiceImpl productService;

    public AuthService(){
        //activeUserList = new ArrayList<>();
        randomString = new RandomString();
        userService = new UserServiceImpl();
        productService = new ProductServiceImpl();
    }

    public boolean checkTokenIsValid(String token){
        for(int i = 0; i < RestApplication.activeUserList.size(); i++){
           if(token.equals(RestApplication.activeUserList.get(i).getToken())){
               return true;
            }
        }
        return false;
    }

    public boolean checkTokenIsValidAndAdmin(String token){
        for(int i = 0; i < RestApplication.activeUserList.size(); i++){
            if(token.equals(RestApplication.activeUserList.get(i).getToken()) && RestApplication.activeUserList.get(i).getRole().equals("Admin")){
                return true;
            }
        }
        return false;
    }
    public boolean checkTokenIsValidAndAdminOrOwn(String token, int id){
        for(int i = 0; i < RestApplication.activeUserList.size(); i++){
            if((token.equals(RestApplication.activeUserList.get(i).getToken()) && RestApplication.activeUserList.get(i).getId() == id) || (
                    token.equals(RestApplication.activeUserList.get(i).getToken()) && RestApplication.activeUserList.get(i).getRole().equals("Admin")
                    )) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTokenIsBelongToSellerOrAdmin(String token, Product product) throws Exception {
        try {
            for(int i = 0; i < RestApplication.activeUserList.size(); i++) {
                if(token.equals(RestApplication.activeUserList.get(i).getToken())){

                    Product tempProd =  this.productService.findById(product.getId()); //Get the right sellerid.

                    if(RestApplication.activeUserList.get(i).getId() == tempProd.getSellerid() ||
                            "Admin".equals(RestApplication.activeUserList.get(i).getRole()) ){
                        return true;
                    }
                    throw new Exception("Wrong Modifier: Update was called from wrong user.");
                }
                throw new Exception("Wrong Token");
            }
            throw new Exception("Other error");
        }
        catch (Exception e) {
            System.out.println("AuthService: checkTokenIsBelongToSellerOrAdmin" + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public LoggedInUser Login(String username, String password) throws Exception {
        try {
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
                        RestApplication.activeUserList.add(justLoggedInUser);
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

    public boolean Logout(String token){
        System.out.println("AuthService - logout elotti db: " + RestApplication.activeUserList.size());
        for(int i = 0; i < RestApplication.activeUserList.size(); i++){
            if(token.equals(RestApplication.activeUserList.get(i).getToken())){
                System.out.println("Logged out user: "+RestApplication.activeUserList.get(i).getUsername());
                RestApplication.activeUserList.remove(i);
                System.out.println("AuthService - logout utani db: " + RestApplication.activeUserList.size());
                return true;
            }
        };
        return false;
    }

    private void CheckUserCanlLogin(LoggedInUser justLoggedInUser){
        if(RestApplication.activeUserList.size() > 0){
            RestApplication.activeUserList.forEach((i) -> validateUserIsLoggedIn(i,justLoggedInUser));
        }
    }
    private void validateUserIsLoggedIn(LoggedInUser alreadyLoggedIn, LoggedInUser tryToLogin){
        if (alreadyLoggedIn.getId() == tryToLogin.getId()){
            newUserIsAlreadLoggedIn = true; //The tryToLogin User already logged in theActiveUser alreadyLoggedIn system, denie the try to login again
        }
    }

    public int getActiveUserListSize(){
        return RestApplication.activeUserList.size();
    }

}
