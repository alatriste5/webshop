package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.model.Address;

import java.sql.SQLException;

public interface AddressService {
    int createAddress(Address address) throws Exception;
    boolean deleteAddress(int id);
    Address findById(int id);
    boolean updateAddress(Address address) throws Exception;
}
