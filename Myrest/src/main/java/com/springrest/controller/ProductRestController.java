package com.springrest.controller;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.OrderException;
import com.springrest.exception.ProductException;
import com.springrest.model.Cart;
import com.springrest.model.CartItem;
import com.springrest.model.Customer;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.model.Product;
import com.springrest.repository.CartItemRepository;
import com.springrest.repository.CustomerRepository;
import com.springrest.repository.OrderRepository;
import com.springrest.repository.OrderItemDao;
import com.springrest.repository.ProductRepository;
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderItemServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/rest")
public class ProductRestController {
	
	
	Log log=LogFactory.getLog(ProductRestController.class);
	@GetMapping("/hello")
	public String sayHiLogger()
	{
		log.info("Spring rest info");
		log.error("error");
		log.fatal("fatal");
		log.debug("debug");
		log.warn("warning");
		return "hi";
	}
	
	@Autowired
	ProductRepository pd;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	ProductServiceImpl productService;
	
	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	OrderItemServiceImpl orderItemService;
	
	
	
	@Autowired
	CartItemServiceImpl cartItemService;
	
	
	
	@PostMapping("/add")
	public void addCustomer(@RequestBody Customer c)
	{
		Cart ck=new Cart();
		c.setCart(ck);
		customerService.addCustomer(c);
	}
	
	@PostMapping("/addcart/{userId}/{itemId}")
	public void addToCart(@PathVariable("userId") String user,@PathVariable("itemId") int item) throws ProductException, CustomerException
	{
		
		Customer c=customerService.getCutomerById(user);
		Cart cart=c.getCart();
		double cartPrice=cart.getTotalPrice();
		
		Product p=productService.getProductById(item);
		cart.setTotalPrice(cartPrice+p.getProductPrice());
		List<CartItem> cartItems=getKart(user);
	   	 for (int i = 0; i < cartItems.size(); i++) 
	   	 {
	   		 CartItem cartItem = cartItems.get(i);
	   		 if (p.getId() == (cartItem.getProduct().getId()))
	   		 {
	   			 cartItem.setQuantity(cartItem.getQuantity() + 1);
	   			// cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getProductPrice());
	   			 cartItemService.addCartItem(cartItem);
	   			 return;
	   		 }
	   	 }
	   	 
	   	 CartItem cartItem = new CartItem();
	   	 cartItem.setQuantity(1);
	   	 cartItem.setProduct(p);
	   	 //cartItem.setPrice(p.getProductPrice());
	   	 cartItem.setCart(c.getCart());
	   	 cartItemService.addCartItem(cartItem);
		
	}
	
	@PostMapping("/addproduct")
	public void addProduct(@RequestBody Product p)
	{
		//Product p=new Product();
		productService.addProduct(p);
	}
	
	@GetMapping("/getkart/{userId}")
	
	public List<CartItem> getKart(@PathVariable("userId") String user) throws CustomerException
	{
		Customer c=customerService.getCutomerById(user);
		//System.out.println(c.getCart());
		//System.out.println(c.getCart().getCartItem());
		return c.getCart().getCartItem();
	}
	
	@GetMapping("/getc")
	public List<Customer> getCust()
	{
		return customerService.getCutomers();
	}
	
	@GetMapping("/getp")
	public List<Product> getProduct()
	{
		
		return pd.findAll();
	}
	
	@PostMapping("/buykart/{userId}")
	public void buyKart(@PathVariable("userId") String user) throws CustomerException
	{
		//double 
		Order order=new Order();
		Customer customer=customerService.getCutomerById(user);
		order.setCustomer(customer);
		order.setPrice(customer.getCart().getTotalPrice());
		orderService.addOrder(order);
		List<CartItem> l=getKart(user);
		System.out.println(l);
		//ListIterator<CartItem> k= l.listIterator();
		for(CartItem k:l)
		{
			OrderItem o=new OrderItem();
			
			o.setPrice(k.getProduct().getProductPrice()*k.getQuantity());
			o.setQuantity(k.getQuantity());
			o.setProduct(k.getProduct());
			o.setOrder(order);
			//removeKartItem(k.next().getId());
			//ci.delete(k.next());
			orderItemService.addOrdItem(o);
		}
		customer.getCart().setTotalPrice(0);
		cartItemService.reomveCartList(l);
		
	}
	
	@DeleteMapping("deletep/{id}")
	public void deleteProduct(@PathVariable("id") int id) throws ProductException
	{
		productService.deleteProduct(id);
	}
	
	@DeleteMapping("/remove/{kartitemid}")
	public String removeKartItem(@PathVariable("kartitemid") int id) throws CartItemException
	{
		//CartItem c=cartItemService.getCartItemById(id);
		//Cart cart=c.getCart();
		
		
		//cart.deleteKartItem(c);
		cartItemService.removeCartItemById(id);
		return "huu"+id;
		
	}
	
	@PutMapping("updatekartitem")
	public void updateItem(@RequestBody CartItem c)
	{
		cartItemService.updateCartItem(c);
	}
	
	@GetMapping("/getproduct/{pid}")
	public Product getProduct(@PathVariable("pid") int id) throws ProductException
	{
		return productService.getProductById(id);
	}
	
	@PostMapping("/getcartitemid/{cid}")
	public CartItem getCartItemId(@PathVariable("cid") int id) throws CartItemException
	{
		return cartItemService.getCartItemById(id);
	}
	
	@GetMapping("getorders/{user}")
	public List<Order> getOrderList(@PathVariable("user") String user) throws CustomerException
	{
		return customerService.getCutomerById(user).getOrderList();
	}
	
	@GetMapping("getorderitems/{oid}")
	public List<OrderItem> getOrderList(@PathVariable("oid") int oid) throws OrderException
	{
		return orderService.getOrderById(oid).getOrderitemList();
	}
	
	
	//@GetMapping("vieworder/{}")
}

