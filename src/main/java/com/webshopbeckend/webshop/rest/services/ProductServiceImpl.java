package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    private ArrayList<Product> productList;

    @Inject
    public ProductServiceImpl() {
        this.productList = new ArrayList<>();
    }

    @Override
    public boolean addProduct(Product product) {
        try {
            if (RestApplication.con != null) {
                String sql = "INSERT INTO `products` (`id`, `name`, `price`," +
                        "`details`, `sellerid`, `customerid`, `image`) VALUES (NULL, '"
                        + product.getName() + "', '"
                        + product.getPrice() + "', '"
                        + product.getDetails() + "', '"
                        + product.getSellerid() + "', '"
                        + product.getCustomerid() + "', '"
                        + product.getImage() + "');";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
        /*System.out.println(product);
        return true;*/
    }

    @Override
    public Product findById(int id) {
        boolean hasres = false;
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM products JOIN user ON user.id = products.sellerid WHERE products.id = " + id + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Product temp = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getString("details"),
                            rs.getInt("sellerid"),
                            rs.getInt("customerid"),
                            rs.getString("image")
                    );
                    hasres = true;
                    return temp;
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
    public List<Product> findAllProduct() {
        productList.clear();
        //prodWithUserList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM products";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getString("details"),
                            rs.getInt("sellerid"),
                            rs.getInt("customerid"),
                            rs.getString("image")
                    );
                    /*User u = new User(
                            rs.getString("username")
                    );*/
                    productList.add(p);
                    //ProductWithUsername pwU = new ProductWithUsername(p,u);
                    //prodWithUserList.add(pwU);
                }
                //return prodWithUserList;
                return productList;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Product> findAllValidProduct() {
        productList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM products JOIN user ON user.id = products.sellerid WHERE valid = 1";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getString("details"),
                            rs.getInt("sellerid"),
                            rs.getInt("customerid"),
                            rs.getString("image")
                    );
                    productList.add(p);
                }
                return productList;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int productCount() {
        this.findAllProduct();
        return productList.size();
    }

    @Override
    public boolean deleteById(int id) {
        try {
            if (RestApplication.con != null) {
                String sql = "DELETE FROM `products` WHERE `products`.`id` = " + id + ";";
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
    public Product updateProduct(Product product) {
        try {
            if (RestApplication.con != null) {
                Product currentProd = findById(product.getId());

                String tempname = product.getName();
                int tempPrice = product.getPrice();
                String tempDetails = product.getDetails();
                /*int tempSeller = product.getSellerid();*/
                int tempCustomer = product.getCustomerid();
                String tempImage = product.getImage();

                if ((currentProd.getName() != tempname) && (tempname != null))
                    currentProd.setName(tempname);
                if ((currentProd.getPrice() != tempPrice) && (tempPrice != 0))
                    currentProd.setPrice(tempPrice);
                if((currentProd.getDetails() != tempDetails) && (tempDetails != null))
                    currentProd.setDetails(tempDetails);
                /*if ((currentProd.getSellerid() != tempSeller) && (tempSeller != 0))
                    currentProd.setSellerid(tempSeller);*/
                if ((currentProd.getCustomerid() != tempCustomer) && (tempCustomer != 0))
                    currentProd.setCustomerid(tempCustomer);
                if((currentProd.getImage() != tempImage) && (tempImage != null))
                    currentProd.setImage(tempImage);

                String sql = "UPDATE `products` SET " +
                        "`name` = '" + currentProd.getName() + "', " +
                        "`price` = '" + currentProd.getPrice() + "', " +
                        "`details` = '" + currentProd.getDetails() + "', " +
                        "`customerid` = '" + currentProd.getCustomerid() + "', " +
                        "`image` = '" + currentProd.getImage() + "' " +
                        "WHERE `products`.`id` = " + currentProd.getId() + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();

                return currentProd;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    //002
    /*
    public Response ShowImagetest2(){
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM imagetest WHERE imagetest.id = 1;";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Product temp = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getString("details"),
                            rs.getInt("sellerid"),
                            rs.getInt("customerid"),
                            rs.getString("image")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
*/

    //001 probálkozá
    /*
    public Response ShowImagetest() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\balaz\\OneDrive\\Asztali gép\\letöltés.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageData = baos.toByteArray();
            return Response.ok(imageData).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

     */
}
