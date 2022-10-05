package com.springrest.service;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.dao.*;
import com.springrest.exception.CartException;
import com.springrest.model.*;

@Service
public class CartServiceImpl implements CartService {

	Log log=LogFactory.getLog(CartServiceImpl.class);
	
	@Autowired
	Environment env;
	String s;
	
	@PostConstruct
	public void postConstruct()
	{
		this.s=env.getProperty("NOCART");
	}
	@Autowired
	private CartDao cartDao;

	public Cart getCartById(int cartId) throws CartException {
		if(cartDao.existsById(cartId))
		{
			log.info(env.getProperty("GETCART"));
			return cartDao.findById(cartId).get();
		}
		log.error(s);
		throw new CartException(s);
	}
}
