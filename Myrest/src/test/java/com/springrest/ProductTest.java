package com.springrest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.springrest.exception.ProductException;
import com.springrest.model.Product;
import com.springrest.service.ProductServiceImpl;

@SpringBootTest
public class ProductTest {

	@Autowired
	Environment env;
	
	@Autowired
	ProductServiceImpl productTest;

	@Test
	public void getAllProductsTest() {
		assertEquals(7,productTest.getAllProducts().size());
	}

	@Test
	public void getProductByIdTest() throws ProductException 
	{
		assertNotNull(productTest.getProductById(2));
		
		assertThrows(ProductException.class,()->productTest.getProductById(8));
	}
	
	@Test
	public void DeleteProductTest() throws ProductException {
		assertEquals(env.getProperty("DELETEP"),productTest.getProductById(9));
	}
	
	@Test
	public void SearchProductByBrand() {
		
		assertEquals(1,productTest.getProductsByBrand("football").size());
	}
	
	@Test
	public void SearchProductByCategory() {
		
		assertEquals(1,productTest.getProductsByCategory("football").size());
	}

}
