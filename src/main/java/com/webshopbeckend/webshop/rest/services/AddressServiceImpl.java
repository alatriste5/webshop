package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.Address;
import com.webshopbeckend.webshop.rest.services.encryption.Decoder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;

@ApplicationScoped
public class AddressServiceImpl implements AddressService{


    @Inject
    public AddressServiceImpl(){
    }

    @Override
    public int createAddress(Address address) throws Exception{
        try {
            if (RestApplication.con != null) {
                int id = lastIdplusone();
                String sql = "INSERT INTO `address` (`id`, `country`, `city`, `postcode`, `street`, `house`)" +
                        "VALUES ('" +
                        id + "', '" +
                        address.getCountry() + "', '" +
                        address.getCity() + "', '" +
                        address.getPostcode() + "', '" +
                        address.getStreet() + "', '" +
                        address.getHouse() + "');";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();
                return id;
            }
            throw new Exception("Can not add this address");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public int lastIdplusone(){
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM address ORDER BY id DESC LIMIT 0, 1";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    int maxId = rs.getInt("id");
                    maxId++;
                    return maxId;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Integer.parseInt(null);
    }

    @Override
    public boolean deleteAddress(int id) {
        try {
            if (RestApplication.con != null) {
                String sql = "DELETE FROM `address` WHERE `address`.`id` = " + id + ";";
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
    public Address findById(int id) {
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM address WHERE address.id = " + id + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Address address = new Address(
                      rs.getInt("id"),
                      rs.getString("country"),
                      rs.getInt("postcode"),
                      rs.getString("city"),
                      rs.getString("street"),
                      rs.getInt("house")
                    );
                    return address;
                }
            }
        } catch (Exception e) {
            System.out.println("AddressServiceImpl - findById error: ");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateAddress(Address newAddress) throws SQLException {
        try {
            if (RestApplication.con != null) {

                String sql = "UPDATE `address` SET " +
                        "`country` = '" + newAddress.getCountry() + "', " +
                        "`postcode` = " + newAddress.getPostcode() + ", " +
                        "`city` = '" + newAddress.getCity() + "', " +
                        "`street` = '" + newAddress.getStreet() + "', " +
                        "`house` = " + newAddress.getHouse() +
                        " WHERE `address`.`id` = " + newAddress.getId() + ";";

                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.out.println("AddressServiceImpl: " + e.getMessage());
        }
        return false;
    }
}
