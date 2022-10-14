package com.springrest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.springrest.exception.CustomerException;
import com.springrest.exception.ProductException;
import com.springrest.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerTest {

	@Autowired
	Environment env;
	
	@Autowired
	CustomerServiceImpl customerTest;
	
	@Test
	@Disabled
	public void getAllCustomerTest() {
		assertEquals(7,customerTest.getCutomers().size());
	}
	
	@Test
	public void getCustomerByIdTest() throws CustomerException 
	{
		assertNotNull(customerTest.getCutomerById("kiran07@gmail.com"));
		
		assertThrows(CustomerException.class,()->customerTest.getCutomerById("kiran07@gmail.com"));
	}
	
}

