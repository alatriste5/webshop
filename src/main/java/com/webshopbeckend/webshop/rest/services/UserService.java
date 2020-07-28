package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user) throws Exception;
    User findById(int id);
    User getUsername(int id);
    List<User> findAllUser();
    boolean deleteById(int id);
    boolean updateUser(User user, boolean isadmin) throws Exception;
    boolean updateUserAddressId(User user);
    boolean updateUserAsAdmin(User user);
}
