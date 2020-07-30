package com.webshopbeckend.webshop.rest.services;

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
import java.util.Date;


public class AuthService {

    public static String secretKey = SecretKey.getSecretKey();
    RandomString randomString;
    boolean newUserIsAlreadLoggedIn;
    UserServiceImpl userService;
    ProductServiceImpl productService;

    ArrayList<LoggedInUser> activeUserList;

    public AuthService(){
        randomString = new RandomString();
        userService = new UserServiceImpl();
        productService = new ProductServiceImpl();
        ActiveUserList aul = ActiveUserList.getInstance();
        activeUserList = aul.getActiveUserList();
    }

    public boolean checkTokenIsValid(String token){
        for(int i = 0; i < activeUserList.size(); i++){
           if(token.equals(activeUserList.get(i).getToken())){
               activeUserList.get(i).setRegisteredIn(new Date());
               return true;
            }
        }
        return false;
    }

    public boolean checkTokenIsValidAndAdmin(String token){
        for(int i = 0; i < activeUserList.size(); i++){
            if(token.equals(activeUserList.get(i).getToken()) && activeUserList.get(i).getRole().equals("Admin")){
                activeUserList.get(i).setRegisteredIn(new Date());
                return true;
            }
        }
        return false;
    }
    public String checkTokenIsValidAndAdmin2(String token, String password){
        for(int i = 0; i < activeUserList.size(); i++){
            if(token.equals(activeUserList.get(i).getToken())) {
                if(activeUserList.get(i).getRole().equals("Admin")){
                    String oldpsw = userService.getUserPassword(activeUserList.get(i).getId());
                    if(oldpsw.equals(Decoder.encrypt(password, secretKey))){
                        activeUserList.get(i).setRegisteredIn(new Date());
                        return "ok";
                    }
                    return "Admin password wasn't correct";
                }
                return "You are not admin";
            }
            return "Token not found";
        }
        return "List was empty";
    }


    public boolean checkTokenIsValidAndAdminOrOwn(String token, int id){
        for(int i = 0; i < activeUserList.size(); i++){
            if((token.equals(activeUserList.get(i).getToken()) && activeUserList.get(i).getId() == id) || (
                    token.equals(activeUserList.get(i).getToken()) && activeUserList.get(i).getRole().equals("Admin")
                    )) {
                activeUserList.get(i).setRegisteredIn(new Date());
                return true;
            }
        }
        return false;
    }

    public boolean checkTokenIsBelongToSellerOrAdmin(String token, Product product) throws Exception {
        try {
            for(int i = 0; i < activeUserList.size(); i++) {
                if(token.equals(activeUserList.get(i).getToken())){

                    Product tempProd =  this.productService.findById(product.getId()); //Get the right sellerid.

                    if(activeUserList.get(i).getId() == tempProd.getSellerid() ||
                            "Admin".equals(activeUserList.get(i).getRole()) ){
                        activeUserList.get(i).setRegisteredIn(new Date());
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
                String sql = "SELECT * FROM user WHERE valid = 0 AND user.username = '" + username + "' AND user.password = '" +
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

    public boolean Logout(String token){
        int before = activeUserList.size();
        for(int i = 0; i < activeUserList.size(); i++){
            if(token.equals(activeUserList.get(i).getToken())){
                System.out.println("Logged out user: "+activeUserList.get(i).getUsername());
                activeUserList.remove(i);
                System.out.println("AuthService - logout elotti db: " + before + " utani db: " + activeUserList.size());
                return true;
            }
        };
        return false;
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
