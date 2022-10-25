package com.springrest.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.ProductException;
import com.springrest.model.Cart;
import com.springrest.model.CartItem;
import com.springrest.model.Customer;
import com.springrest.model.Product;
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/cart")
public class CartRestController {
	
	Log log=LogFactory.getLog(CartRestController.class);

	@Autowired
	Environment env;
	
	@Autowired
	CartItemServiceImpl cartItemService;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	ProductServiceImpl productService;
	
	
	//1.getting cart items
	@GetMapping("/getcart/{userId}")	
	public ResponseEntity<?> getCart(@PathVariable("userId") String user) throws CustomerException
	{
		Customer c=customerService.getCutomerById(user);
		//log.info(env.getProperty("GPIC"));
		//System.out.println(c.getCart());
		//System.out.println(c.getCart().getCartItem());
		return new ResponseEntity<List<CartItem>>(c.getCart().getCartItem(),HttpStatus.FOUND);

	}
	
	//2.adding to cart
	@PostMapping("/addcart/{userId}/{productid}")
	public ResponseEntity<?> addToCart(@PathVariable("userId") String user,@PathVariable("productid") int item) throws ProductException, CustomerException, CartItemException
	{
		
//		Customer customer=customerService.getCutomerById(user);
//		Cart cart=customer.getCart();
//		//double cartPrice=cart.getTotalPrice();
//		
//		Product p=productService.getProductById(item);
//		//cart.setTotalPrice(cartPrice+p.getProductPrice());
//		List<CartItem> cartItems=customer.getCart().getCartItem();
//		
//	   	 for (int i = 0; i < cartItems.size(); i++) 
//	   	 {
//	   		 CartItem cartItem = cartItems.get(i);
//	   		 if (p.getId() == (cartItem.getProduct().getId()))
//	   		 {
//	   			 cartItem.setQuantity(cartItem.getQuantity() + 1);
//	   			// cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getProductPrice());
//	   			 cartItemService.addCartItem(cartItem);
//	   			return new ResponseEntity<String>(env.getProperty("ADDTOCART"),HttpStatus.ACCEPTED);
//
//	   		 }
//	   	 }
//	   	 
//	   	 CartItem cartItem = new CartItem();
//	   	 cartItem.setQuantity(1);
//	   	 cartItem.setProduct(p);
//	   	// cartItem.setPrice(p.getProductPrice());
//	   	 cartItem.setCart(customer.getCart());
	   	
	   	// log.info(customer.getUserName()+" "+p.getProductName()+" "+env.getProperty("ADDTOCART"));
		cartItemService.addCartItem(user,item);
		return new ResponseEntity<String>(env.getProperty("ADDTOCART"),HttpStatus.CREATED);
	 
		
	}
	
	//3.deleting from cart
	@DeleteMapping("/removefromcart/{kartitemid}")
	public ResponseEntity<?> removeKartItem(@PathVariable("kartitemid") int id) throws CartItemException
	{
		//CartItem c=cartItemService.getCartItemById(id);
		//Cart cart=c.getCart();
		
		
		//cart.deleteKartItem(c);
		//log.info(cartItemService.getCartItemById(id).getCart().getCustomer().getUserName()+"removed "+cartItemService.getCartItemById(id).getProduct().getProductName()+"removed cart");
		
		//return "huu"+id;
		cartItemService.removeCartItemById(id);
		return new ResponseEntity<String>(env.getProperty("RPFC"),HttpStatus.ACCEPTED);

	}
	
	//4.updating quantity of item in cart
	@PutMapping("/updatecartitem/{id}/{quantity}")
	public ResponseEntity<?> updateItem(@PathVariable("id") int cartItemId,@PathVariable("quantity") int quantity) throws CartItemException
	{
		//CartItem cartItem=cartItemService.getCartItemById(cartItemId);
		//cartItem.setPrice(cartItem.getProduct().getProductPrice()*quantity);
		//cartItem.setQuantity(quantity);
		
		//log.info(cartItemService.getCartItemById(cartItemId).getCart().getCustomer().getUserName()+" updated quantity of");
	
		cartItemService.updateCartItem(cartItemId,quantity);
		return new ResponseEntity<String>(env.getProperty("CQOCI"),HttpStatus.ACCEPTED);

	}
}
