package com.springrest.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.OrderException;
import com.springrest.exception.PaymentTypeException;
import com.springrest.exception.ProductException;
import com.springrest.model.CartItem;
import com.springrest.model.Customer;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.model.PaymentType;
import com.springrest.model.Product;
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderItemServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.PaymentTypeServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/buy")
public class BuyRestController {
	
	Log log=LogFactory.getLog(BuyRestController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	PaymentTypeServiceImpl paymentTypeService;
	
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
	@PostMapping("/buycart/{userId}/{ptype}")
	public ResponseEntity<?> buyKart(@PathVariable("userId") String user,@PathVariable("ptype") int ptid) throws CustomerException, CartItemException, OrderException, PaymentTypeException
	{
		//double 
		
//		Customer customer=customerService.getCutomerById(user);
//	
//		List<CartItem> l=customer.getCart().getCartItem();
//		//System.out.println(l);
//		//ListIterator<CartItem> k= l.listIterator();
//		if(l.isEmpty())
//		{
//			log.error(env.getProperty("EMPTY"));
//			throw new CartItemException(env.getProperty("EMPTY"));
//		
//		}
//
//		
//		Order order=new Order();
//		order.setDate(new Date());
//		order.setCustomer(customer);
//		PaymentType paymentType=paymentTypeService.getPaymentTypeById(ptid);
//		order.setpType(paymentType);
//		if(ptid==1)
//			order.setOrderStatus(env.getProperty("OP"));
//		else
//			order.setOrderStatus(env.getProperty("OPP"));
//		orderService.addOrder(order);
//		
//		double price=0;
//		for(CartItem k:l)
//		{
//			
//			OrderItem o=new OrderItem();
//			
//			double oPrice=k.getProduct().getProductPrice()*k.getQuantity();
//			price=price+oPrice;
//			o.setQuantity(k.getQuantity());
//			o.setPrice(oPrice);
//			o.setProduct(k.getProduct());
//			
//			o.setOrder(order);
//			//removeKartItem(k.next().getId());
//			//ci.delete(k.next());
//			k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
//			productService.updateProduct(k.getProduct());
//			orderItemService.addOrdItem(o);
//		}
//		order.setPrice(price);
//		//customer.getCart().setTotalPrice(0);
//		cartItemService.reomveCartList(l);
		
		return new ResponseEntity<String>(""+orderService.buyKart(user,ptid),HttpStatus.ACCEPTED);

		//l.clear();
		//log.info(customer.getUserName()+"Bought the cart");
		
	}
	
	//2.buying single item which is cart
	@PostMapping("/buyfromcart/{kartitemid}/{ptid}")
	public ResponseEntity<?> buyFromCart(@PathVariable("kartitemid") int id,@PathVariable("ptid") int ptid) throws CartItemException, OrderException, PaymentTypeException
	{
//		CartItem cartItem=cartItemService.getCartItemById(id);
//		Order order=new Order();
//		order.setDate(new Date());
//		order.setCustomer(cartItem.getCart().getCustomer());
//		order.setPrice(cartItem.getProduct().getProductPrice()*cartItem.getQuantity());
//		PaymentType paymentType=paymentTypeService.getPaymentTypeById(ptid);
//		order.setpType(paymentType);
//		if(ptid==1)
//			order.setOrderStatus(env.getProperty("OP"));
//		else
//			order.setOrderStatus(env.getProperty("OPP"));
//		orderService.addOrder(order);
//		OrderItem orderItem=new OrderItem();
//		orderItem.setPrice(cartItem.getProduct().getProductPrice()*cartItem.getQuantity());
//		orderItem.setProduct(cartItem.getProduct());
//		orderItem.setQuantity(cartItem.getQuantity());
//		orderItem.setOrder(order);
//		cartItem.getProduct().setUnitStock(cartItem.getProduct().getUnitStock()-cartItem.getQuantity());
//		productService.updateProduct(cartItem.getProduct());
//		orderItemService.addOrdItem(orderItem);
//		//log.info(cartItem.getCart().getCustomer().getUserName()+"Bought the item from cart");
//		crc.removeKartItem(id);
//		return new ResponseEntity<String>(""+order.getPrice(),HttpStatus.ACCEPTED);
		
		return new ResponseEntity<String>(""+orderService.buyFromKart(id, ptid),HttpStatus.ACCEPTED);

	}
	
	//3.directly buying from product
	@PostMapping("/directbuy/{userid}/{productid}/{ptid}")
	public ResponseEntity<?> buyProduct(@PathVariable("userid") String user,@PathVariable("productid") int id,@PathVariable("ptid") int ptid) throws ProductException, CustomerException, OrderException, PaymentTypeException
	{
//		Product product=productService.getProductById(id);
//		Order order=new Order();
//		order.setDate(new Date());
//		order.setCustomer(customerService.getCutomerById(user));
//		order.setPrice(product.getProductPrice());
//		PaymentType paymentType=paymentTypeService.getPaymentTypeById(ptid);
//		order.setpType(paymentType);
//		if(ptid==1)
//			order.setOrderStatus(env.getProperty("OP"));
//		else
//			order.setOrderStatus(env.getProperty("OPP"));
//		orderService.addOrder(order);
//		orderService.addOrder(order);
//		OrderItem orderItem=new OrderItem();
//		orderItem.setPrice(product.getProductPrice());
//		orderItem.setProduct(product);
//		orderItem.setQuantity(1);
//		orderItem.setOrder(order);
//		//k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
//		product.setUnitStock(product.getUnitStock()-1);
//		productService.updateProduct(product);
//		orderItemService.addOrdItem(orderItem);
		//log.info(customerService.getCutomerById(user).getUserName()+"Bought the item from home");
		
		return new ResponseEntity<String>(""+orderService.buyProduct(user, id, ptid),HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/getpaymenttypes")
	public ResponseEntity<?> getProduct()
	{
		log.info("getting the products");
		return new ResponseEntity<List<PaymentType>>(paymentTypeService.getPaymentTypes(),HttpStatus.FOUND);
	}
	
}
