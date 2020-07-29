package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.SecretKey;
import com.webshopbeckend.webshop.rest.model.User;
import com.webshopbeckend.webshop.rest.services.encryption.Decoder;

import java.sql.*;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserServiceImpl  implements UserService {
    //TODO: Add Uname és mail validation ott is működjön

    private ArrayList<User> userList;
    public static String secretKey = SecretKey.getSecretKey();
    private AddressServiceImpl addressService;

    @Inject
    public UserServiceImpl() {
        this.userList = new ArrayList<>();
        addressService = new AddressServiceImpl();
    }

    @Override
    public String addUser(User user) throws Exception {
        try {
            if (RestApplication.con != null) {
                String sql = "INSERT INTO `user` (`id`, `username`, `name`, `email`," +
                        "`password`, `addressid`, `role`) VALUES (NULL, '"
                        + user.getUsername() + "', '"
                        + user.getName() + "', '"
                        + user.getEmail() + "', '"
                        + Decoder.encrypt(user.getPassword(),secretKey) + "', '"
                        + user.getAddressid() + "', '"
                        + "User');";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();
                return user.getUsername();
            }
            throw new Exception("Can not add this user");
        } catch (Exception e) {
            System.out.println("UserServiceImpl: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public User findById(int id) {
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM user WHERE user.id = " + id + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    User temp = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("name"),
                            null,//Decoder.decrypt(rs.getString("password"), secretKey),
                            rs.getString("email"),
                            rs.getInt("addressid"),
                            rs.getString("role"),
                            rs.getDate("createdat")
                    );
                    return temp;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User findByUsername(String username) {
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM user WHERE username = '" + username + "';";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    User temp = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("name"),
                            Decoder.decrypt(rs.getString("password"), secretKey),
                            rs.getString("email"),
                            rs.getInt("addressid"),
                            rs.getString("role"),
                            rs.getDate("createdat")
                    );
                    return temp;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUsername(int id) {
        boolean hasres = false;
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT username FROM user WHERE user.id = " + id + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    User temp2 = new User(rs.getString("username"));
                    hasres = true;
                    return temp2;
                }
            }
            if (!hasres) {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<User> findAllUser() {
        userList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM user";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    User u = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("name"),
                            Decoder.decrypt(rs.getString("password"), secretKey),
                            rs.getString("email"),
                            rs.getInt("addressid"),
                            rs.getString("role"),
                            rs.getDate("createdat")
                    );
                    userList.add(u);
                }
                return userList;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            if (RestApplication.con != null) {
                String sql = "DELETE FROM `user` WHERE `user`.`id` = " + id + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.execute();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user, boolean isadmin) throws Exception {
        try {
            if (RestApplication.con != null) {
                User currentUser = findById(user.getId());
                currentUser.setPassword(getUserPassword(user.getId()));

                String tempPassword = Decoder.encrypt(user.getPassword(),secretKey);

                if(tempPassword.equals(currentUser.getPassword()) || isadmin) {

                    String dataExistCheck = this.checkUsernameOrEmailExist(user,currentUser, false);

                    switch (dataExistCheck) {
                        case "ok":
                            break;
                        case "username":
                            throw new Exception("This username is already exist");
                        case "email":
                            throw new Exception("This email is already exist");
                    }

                    String sql = "UPDATE `user` SET " +
                            "`username` = '" + user.getUsername() + "', " +
                            "`name` = '" + user.getName() + "', " +
                            "`password` = '" + Decoder.encrypt(user.getPassword(), secretKey) + "', " +
                            "`email` = '" + user.getEmail() + "' " +
                            "WHERE id = " + currentUser.getId() + ";";
                    PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                    statement.executeUpdate();

                    return true;
                }
                else {
                    throw new Exception("The gotted password did not match with the current password!");
                }
            }
        } catch (Exception e) {
            System.out.println("UserServiceImpl error updateUser: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return false;
    }

    public String getUserPassword(int id){
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT password FROM user WHERE user.id = " + id + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String password = rs.getString("password");
                    return password;
                }
            }
        } catch (SQLException throwables) {
            System.out.println("UserServiceImpl error - getUserPassword: "+ throwables.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateUserAddressId(User user) {
        try {
            if (RestApplication.con != null) {
                String sql = "UPDATE `user` SET " +
                        "`addressid` = " + user.getAddressid() +
                        " WHERE username = '" +  user.getUsername() + "';";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);

                statement.executeUpdate();

                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUserAsAdmin(User user) {
        try {
            if(RestApplication.con != null) {
                String sql = "UPDATE `user` SET " +
                        "`username` = '" + user.getUsername() + "', " +
                        "`name` = '" + user.getName() + "', " +
                        "`email` = '" + user.getEmail() + "', " +
                        "`role` = '" + user.getRole() + "' " +
                        " WHERE id = " +  user.getId() + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);

                statement.executeUpdate();

                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private String checkUsernameOrEmailExist(User user, User UpdateCurrentUser, boolean newTrueOrUpdateFalse){
        ArrayList<User> allUser = this.findAllUser();
        if(!newTrueOrUpdateFalse){
            for(int i=0; i < allUser.size(); i++) {
                if(allUser.get(i).getId() == UpdateCurrentUser.getId()){
                    allUser.remove(i);
                }
            }
        }

        String tempUsername = user.getUsername();
        String tempEmail = user.getEmail();

        for(int i=0; i < allUser.size(); i++){
            if(tempUsername.equals(allUser.get(i).getUsername())){
                    return "username";
            }
            if(tempEmail.equals(allUser.get(i).getEmail())){
                    return "email";
            }
        }
        return "ok";
    }
}
