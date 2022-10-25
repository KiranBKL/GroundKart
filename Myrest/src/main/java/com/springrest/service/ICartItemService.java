package com.springrest.service;

import org.springframework.http.ResponseEntity;

import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.ProductException;
import com.springrest.model.*;

public interface ICartItemService {
	void addCartItem(String user,int item) throws CustomerException, ProductException;

	void removeCartItemById(int CartItemId) throws CartItemException;

    
}

