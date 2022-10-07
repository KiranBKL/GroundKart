package com.springrest.service;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.exception.CartException;
import com.springrest.model.*;
import com.springrest.repository.*;

@Service
public class CartServiceImpl implements ICartService {

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
	private CartRepository cartDao;

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
