package com.springrest.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.ProductException;
import com.springrest.model.CartItem;
import com.springrest.model.Customer;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.model.Product;
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderItemServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/buy")
public class BuyRestController {
	
	Log log=LogFactory.getLog(BuyRestController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	CartRestController crc;
	
	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	OrderItemServiceImpl orderItemService;
	
	@Autowired
	ProductServiceImpl productService;
	
	@Autowired
	CartItemServiceImpl cartItemService;

	//1.checkout the cart
	@PostMapping("/buycart/{userId}")
	public ResponseEntity<?> buyKart(@PathVariable("userId") String user) throws CustomerException, CartItemException
	{
		//double 

		Customer customer=customerService.getCutomerById(user);
	
		List<CartItem> l=customer.getCart().getCartItem();
		//System.out.println(l);
		//ListIterator<CartItem> k= l.listIterator();
		if(l.isEmpty())
		{
			log.error(env.getProperty("EMPTY"));
			throw new CartItemException(env.getProperty("EMPTY"));
		
		}
		Order order=new Order();
		order.setDate(new Date());
		order.setCustomer(customer);
		orderService.addOrder(order);
		
		double price=0;
		for(CartItem k:l)
		{
			
			OrderItem o=new OrderItem();
			
			double oPrice=k.getProduct().getProductPrice()*k.getQuantity();
			price=price+oPrice;
			o.setQuantity(k.getQuantity());
			o.setPrice(oPrice);
			o.setProduct(k.getProduct());
			
			o.setOrder(order);
			//removeKartItem(k.next().getId());
			//ci.delete(k.next());
			k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
			productService.updateProduct(k.getProduct());
			orderItemService.addOrdItem(o);
		}
		order.setPrice(price);
		//customer.getCart().setTotalPrice(0);
		cartItemService.reomveCartList(l);
		
		return new ResponseEntity<String>(""+price,HttpStatus.ACCEPTED);

		//l.clear();
		//log.info(customer.getUserName()+"Bought the cart");
		
	}
	
	//2.buying single item which is cart
	@PostMapping("/buyfromcart/{kartitemid}")
	public ResponseEntity<?> buyFromCart(@PathVariable("kartitemid") int id) throws CartItemException
	{
		CartItem cartItem=cartItemService.getCartItemById(id);
		Order order=new Order();
		order.setDate(new Date());
		order.setCustomer(cartItem.getCart().getCustomer());
		order.setPrice(cartItem.getProduct().getProductPrice()*cartItem.getQuantity());
		orderService.addOrder(order);
		OrderItem orderItem=new OrderItem();
		orderItem.setPrice(cartItem.getProduct().getProductPrice()*cartItem.getQuantity());
		orderItem.setProduct(cartItem.getProduct());
		orderItem.setQuantity(cartItem.getQuantity());
		orderItem.setOrder(order);
		cartItem.getProduct().setUnitStock(cartItem.getProduct().getUnitStock()-cartItem.getQuantity());
		productService.updateProduct(cartItem.getProduct());
		orderItemService.addOrdItem(orderItem);
		//log.info(cartItem.getCart().getCustomer().getUserName()+"Bought the item from cart");
		crc.removeKartItem(id);
		return new ResponseEntity<String>(""+order.getPrice(),HttpStatus.ACCEPTED);

	}
	
	//3.directly buying from product
	@PostMapping("/directbuy/{userid}/{productid}")
	public ResponseEntity<?> buyProduct(@PathVariable("userid") String user,@PathVariable("productid") int id) throws ProductException, CustomerException
	{
		Product product=productService.getProductById(id);
		Order order=new Order();
		order.setDate(new Date());
		order.setCustomer(customerService.getCutomerById(user));
		order.setPrice(product.getProductPrice());
		orderService.addOrder(order);
		OrderItem orderItem=new OrderItem();
		orderItem.setPrice(product.getProductPrice());
		orderItem.setProduct(product);
		orderItem.setQuantity(1);
		orderItem.setOrder(order);
		//k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
		product.setUnitStock(product.getUnitStock()-1);
		productService.updateProduct(product);
		orderItemService.addOrdItem(orderItem);
		//log.info(customerService.getCutomerById(user).getUserName()+"Bought the item from home");
		
		return new ResponseEntity<String>(""+order.getPrice(),HttpStatus.ACCEPTED);

	}
}
