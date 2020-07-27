package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.model.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);
    Product findById(int id);
    List<Product> findAllProduct();
    List<Product> findAllProductByValid(int val);
    List<Product> findAllProductByCustomeriId(int id);
    int productCount();
    boolean deleteById(int id);
    Product updateProduct(Product product);
    boolean setProductValid(int id);
    List<Product> findProductsBySeller(int sellerid);
    boolean sellProduct(int id,int customerid);

}
