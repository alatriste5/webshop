package com.webshopbeckend.webshop.rest.services;

import com.webshopbeckend.webshop.rest.model.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);
    Product findById(int id);
    List<Product> findAllProduct();
    //List<ProductWithUsername> findAllProduct();
    List<Product> findAllValidProduct();
    int productCount();
    boolean deleteById(int id);
    Product updateProduct(Product product);

}
