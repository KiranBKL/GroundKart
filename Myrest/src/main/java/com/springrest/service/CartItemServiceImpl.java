package com.springrest.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.controller.ProductRestController;
import com.springrest.exception.CartItemException;
import com.springrest.model.*;
import com.springrest.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements ICartItemService {

	Log log=LogFactory.getLog(ICartItemService.class);
	
	@Autowired
	Environment env;
	String s;
    @Autowired
    private CartItemRepository cartItemDao;
    
    @PostConstruct
	public void postConstruct()
	{
		this.s=env.getProperty("NOCARTITEM");
	}

    public void addCartItem(CartItem cartItem) {
    	log.info(cartItem.getCart().getCustomer().getUserName()+" "+env.getProperty("ADDETOCART"));
   	 	cartItemDao.save(cartItem);

    }

    public void removeCartItemById(int cartItemId) throws CartItemException {
    	if(cartItemDao.existsById(cartItemId))
    	{
    		log.info(env.getProperty("REMOVEUSER"));
    		cartItemDao.deleteById(cartItemId);
    		return;
    	}
    	log.error(s);
    	throw new CartItemException(s);
    }

    public void removeAll()
    {
    	log.info(env.getProperty("REMOVEALLCI"));
    	cartItemDao.deleteAll();
    }
    
    public CartItem getCartItemById(int cartItemId) throws CartItemException {
      	 
    	if(cartItemDao.existsById(cartItemId))
    	{
    		log.info(env.getProperty("GCID"));
    		return cartItemDao.findById(cartItemId).get();
    	}
    	log.error(s);
    	throw new CartItemException(s);
		
       }

	public void updateCartItem(CartItem c) {
		// TODO Auto-generated method stub
		log.info(env.getProperty("CQOCI"));
		 cartItemDao.save(c);
	}
	
	public void reomveCartList(List<CartItem> l)
	{
		//System.out.println("calllllllled    "+l);
		log.info(env.getProperty("REMOVEALLCI"));
		cartItemDao.deleteAllInBatch(l);
	}
    
}

