package com.springrest.service;

import java.util.List;

import com.springrest.exception.ProductException;
import com.springrest.model.*;

public interface IProductService {
    
    List<Product> getAllProducts();
    
    Product getProductById(int productId) throws ProductException;

    void deleteProduct(int productId) throws ProductException;
    
    void addProduct(Product product);
    
    void updateProduct(Product product);
}

