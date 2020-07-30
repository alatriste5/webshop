package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.RestApplication;
import com.webshopbeckend.webshop.rest.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
                            rs.getDate("soldat"),
                            rs.getInt("valid"),
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
                            rs.getDate("soldat"),
                            rs.getInt("valid"),
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

    public List<Product> findAllProductByValid(int val) {
        productList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM products WHERE valid = "+val;
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
                            rs.getDate("soldat"),
                            rs.getInt("valid"),
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
    public List<Product> findAllProductByCustomeriId(int id) {
        productList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM products WHERE customerid = "+id;
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
                            rs.getDate("soldat"),
                            rs.getInt("valid"),
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
                String tempImage = product.getImage();

                if ((currentProd.getName() != tempname) && (tempname != null))
                    currentProd.setName(tempname);
                if ((currentProd.getPrice() != tempPrice) && (tempPrice != 0))
                    currentProd.setPrice(tempPrice);
                if((currentProd.getDetails() != tempDetails) && (tempDetails != null))
                    currentProd.setDetails(tempDetails);
                if((currentProd.getImage() != tempImage) && (tempImage != null))
                    currentProd.setImage(tempImage);

                String sql = "UPDATE `products` SET " +
                        "`name` = '" + currentProd.getName() + "', " +
                        "`price` = '" + currentProd.getPrice() + "', " +
                        "`details` = '" + currentProd.getDetails() + "', " +
                        "`image` = '" + currentProd.getImage() + "' " +
                        "WHERE `products`.`id` = " + currentProd.getId() + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();

                return currentProd;

            }
        } catch (Exception e) {
            System.out.println("ProductServiceImpl error updateProduct: ");
            System.out.println(e.getMessage());
        }
        return product;
    }

    @Override
    public boolean setProductValid(int id) {
        try {
            if (RestApplication.con != null) {
                String sql = "UPDATE `products` SET " +
                        "`valid` = 1 " +
                        "WHERE `products`.`id` = " + id + ";";
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
    public List<Product> findProductsBySeller(int id) {
        productList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "SELECT * FROM products WHERE sellerid = " + id;
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
                            rs.getDate("soldat"),
                            rs.getInt("valid"),
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
    public boolean sellProduct(int productid, int customerid) {
        try {
            if (RestApplication.con != null) {

                String sql = "UPDATE `products` SET `customerid` = '" + customerid + "', `valid` = '" + 2 + "', " +
                        "`soldat` = now() WHERE `products`.`id` = " + productid + ";";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.executeUpdate();

                return true;
            }
        } catch (Exception e) {
            System.out.println("ProductServiceImpl error sellProduct: ");
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUnSoldProductsBySeller(int sellerid) {
        productList.clear();
        try {
            if (RestApplication.con != null) {
                String sql = "DELETE FROM `products` WHERE (sellerid = " + sellerid + " && valid = 0) || (sellerid = " + sellerid + " && valid = 1)";
                PreparedStatement statement = RestApplication.con.prepareStatement(sql);
                statement.execute();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
