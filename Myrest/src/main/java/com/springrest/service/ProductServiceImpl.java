package com.springrest.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.controller.ProductRestController;
import com.springrest.exception.ProductException;
import com.springrest.model.*;
import com.springrest.repository.*;

@Service
public class ProductServiceImpl implements IProductService {

	
	Log log=LogFactory.getLog(ProductServiceImpl.class);
	
	@Autowired
	Environment env;
	String s;
    @Autowired
    private ProductRepository productDao;
    
    @PostConstruct
	public void postConstruct()
	{
		this.s=env.getProperty("NOPRODUCT");
	}

    public List<Product> getAllProducts() {
    	log.info(env.getProperty("VIEWPRODUCTS"));
   	 return productDao.findAll();
    }

    public Product getProductById(int productId) throws ProductException {
    	if(productDao.existsById(productId))
    	{
    		log.info(env.getProperty("PRODUCT"));
    		return productDao.findById(productId).get();
    	}
    	log.error(s);
    	throw new ProductException(s);
    }
    
    public void deleteProduct(int productId) throws ProductException {
    	if(productDao.existsById(productId))
    	{
    		log.info(env.getProperty("DELETEP"));
    		productDao.deleteById(productId);
    		return;
    	}
    	log.error(s);
    	throw new ProductException(s);
    }
    
    public void addProduct(Product product){
    	log.info(env.getProperty("ADDPRODUCT"));
   	 productDao.save(product);
    }
    
    public void updateProduct(Product product){
    	log.info(env.getProperty("UPDATEP"));
   	 productDao.save(product);
    }
    
    public List<Product> getProductsByCategory(String category) {
    	log.info(env.getProperty("VPBC")+" "+category);
   	 return productDao.getProductsByCategory(category);
    }
    
    public List<Product> getProductsByName(String name) {
    	log.info(env.getProperty("VPBN")+" "+name);
   	 return productDao.getProductsByName(name);
    }
    
    public List<Product> getProductsByBrand(String brand) {
    	log.info(env.getProperty("VPBB"));
   	 return productDao.getProductsBybrand(brand);
    }
    
}

