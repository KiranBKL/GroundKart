package com.springrest.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springrest.controller.ProductRestController;
import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.ProductException;
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
    
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	ProductServiceImpl productService;
    
    @PostConstruct
	public void postConstruct()
	{
		this.s=env.getProperty("NOCARTITEM");
	}

    public void addCartItem(String user,int item) throws CustomerException, ProductException {
    	
		Customer customer=customerService.getCutomerById(user);
		Cart cart=customer.getCart();
		//double cartPrice=cart.getTotalPrice();
		
		Product p=productService.getProductById(item);
		//cart.setTotalPrice(cartPrice+p.getProductPrice());
		List<CartItem> cartItems=customer.getCart().getCartItem();
		
	   	 for (int i = 0; i < cartItems.size(); i++) 
	   	 {
	   		 CartItem cartItem = cartItems.get(i);
	   		 if (p.getId() == (cartItem.getProduct().getId()))
	   		 {
	   			 cartItem.setQuantity(cartItem.getQuantity() + 1);
	   			// cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getProductPrice());
	   			// cartItemService.addCartItem(cartItem);
	   			cartItemDao.save(cartItem);
	   			log.info(cartItem.getCart().getCustomer().getUserName()+" "+env.getProperty("ADDETOCART"));
	   			return;

	   		 }
	   	 }
	   	 
	   	 CartItem cartItem = new CartItem();
	   	 cartItem.setQuantity(1);
	   	 cartItem.setProduct(p);
	   	// cartItem.setPrice(p.getProductPrice());
	   	 cartItem.setCart(customer.getCart());
	   	cartItemDao.save(cartItem);
    	log.info(cartItem.getCart().getCustomer().getUserName()+" "+env.getProperty("ADDETOCART"));
    	return ;
   	 	

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

	public void updateCartItem(int cartItemId,int quantity) throws CartItemException {
		// TODO Auto-generated method stub
		CartItem cartItem=getCartItemById(cartItemId);
		//cartItem.setPrice(cartItem.getProduct().getProductPrice()*quantity);
		cartItem.setQuantity(quantity);
		log.info(env.getProperty("CQOCI"));
		 cartItemDao.save(cartItem);
		 
	}
	
	public void reomveCartList(List<CartItem> l)
	{
		//System.out.println("calllllllled    "+l);
		log.info(env.getProperty("REMOVEALLCI"));
		cartItemDao.deleteAllInBatch(l);
	}
	
	
    
}

