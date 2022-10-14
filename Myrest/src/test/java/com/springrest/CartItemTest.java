package com.springrest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.springrest.exception.CartItemException;
import com.springrest.exception.ProductException;
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;

@SpringBootTest
public class CartItemTest {

	@Autowired
	Environment env;
	
	@Autowired
	CartItemServiceImpl cartItemTest;
	
	@Test
	public void DeleteTest() throws CartItemException {
		assertEquals(env.getProperty("DELETEP"),cartItemTest.getCartItemById(99));
	}
	
	@Test
	public void getCartItemByIdTest() throws CartItemException
	{
		assertNotNull(cartItemTest.getCartItemById(99));
		
		assertThrows(CartItemException.class,()->cartItemTest.getCartItemById(99));
	}
}
