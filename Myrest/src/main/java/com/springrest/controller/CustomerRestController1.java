package com.springrest.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderItemServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/user")
public class CustomerRestController1 {
	
	@Autowired
	Environment env;
	
	Log log=LogFactory.getLog(CustomerRestController1.class);

	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	OrderItemServiceImpl orderItemService;
	
	@Autowired
	CartItemServiceImpl cartItemService;
	
	@Autowired
	ProductServiceImpl productService;
	
	@Autowired
	CustomerServiceImpl customerService;
	
//	@Autowired
//	Customer customer;
//	
//	@Autowired
//	Cart cart;
//	
//	@Autowired
//	Order order;
//	
//	@Autowired
//	OrderItem orderItem;
//	
//	@Autowired
//	CartItem cartItem;
//	
//	@Autowired
//	Product product;
	
	//1.adding customer
	@PostMapping("/addcustomer")
	public void addCustomer(@RequestBody Customer c)
	{
		Cart cart=new Cart();
		c.setCart(cart);
		customerService.addCustomer(c);
		//log.info(c.getUserName()+" "+env.getProperty("REGISTER"));
	}
	
	//2.getting products
	@GetMapping("/getproducts")
	public List<Product> getProduct()
	{
		//log.info(" "+env.getProperty("VIEWPRODUCTS"));
		return productService.getAllProducts();
	}
	
	//3.getting product by id
	@GetMapping("/getproduct/{pid}")
	public Product getProduct(@PathVariable("pid") int id) throws ProductException
	{
		//log.info(env.getProperty("PRODUCT"));
		return productService.getProductById(id);
	}
	
	//4.getting cart items
	@GetMapping("/getcart/{userId}")	
	public List<CartItem> getCart(@PathVariable("userId") String user) throws CustomerException
	{
		Customer c=customerService.getCutomerById(user);
		//log.info(env.getProperty("GPIC"));
		//System.out.println(c.getCart());
		//System.out.println(c.getCart().getCartItem());
		return c.getCart().getCartItem();
	}
	
	//5.adding to cart
	@PostMapping("/addcart/{userId}/{productid}")
	public void addToCart(@PathVariable("userId") String user,@PathVariable("productid") int item) throws ProductException, CustomerException, CartItemException
	{
		
		Customer customer=customerService.getCutomerById(user);
		Cart cart=customer.getCart();
		//double cartPrice=cart.getTotalPrice();
		
		Product p=productService.getProductById(item);
		//cart.setTotalPrice(cartPrice+p.getProductPrice());
		List<CartItem> cartItems=getCart(user);
		
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
	   	// cartItem.setPrice(p.getProductPrice());
	   	 cartItem.setCart(customer.getCart());
	   	 cartItemService.addCartItem(cartItem);
	   	// log.info(customer.getUserName()+" "+p.getProductName()+" "+env.getProperty("ADDTOCART"));
		
	}
	
	//6.deleting from cart
	@DeleteMapping("/removefromcart/{kartitemid}")
	public void removeKartItem(@PathVariable("kartitemid") int id) throws CartItemException
	{
		//CartItem c=cartItemService.getCartItemById(id);
		//Cart cart=c.getCart();
		
		
		//cart.deleteKartItem(c);
		//log.info(cartItemService.getCartItemById(id).getCart().getCustomer().getUserName()+"removed "+cartItemService.getCartItemById(id).getProduct().getProductName()+"removed cart");
		cartItemService.removeCartItemById(id);
		//return "huu"+id;
		
	}
	
	//7.updating quantity of item in cart
	@PutMapping("/updatecartitem/{id}/{quantity}")
	public void updateItem(@PathVariable("id") int cartItemId,@PathVariable("quantity") int quantity) throws CartItemException
	{
		CartItem cartItem=cartItemService.getCartItemById(cartItemId);
		//cartItem.setPrice(cartItem.getProduct().getProductPrice()*quantity);
		cartItem.setQuantity(quantity);
		cartItemService.updateCartItem(cartItem);
		//log.info(cartItemService.getCartItemById(cartItemId).getCart().getCustomer().getUserName()+" updated quantity of");
	}
	
	//8.checkout the cart
	@PostMapping("/buycart/{userId}")
	public void buyKart(@PathVariable("userId") String user) throws CustomerException, CartItemException
	{
		//double 
		Order order=new Order();
		order.setDate(new Date());
		Customer customer=customerService.getCutomerById(user);
		order.setCustomer(customer);
		//order.setPrice(customer.getCart().getTotalPrice());
		orderService.addOrder(order);
		List<CartItem> l=getCart(user);
		//System.out.println(l);
		//ListIterator<CartItem> k= l.listIterator();
		if(l.isEmpty())
		{
			log.error(env.getProperty("EMPTY"));
			throw new CartItemException(env.getProperty("EMPTY"));
		
		}
		for(CartItem k:l)
		{
			OrderItem o=new OrderItem();
			
			
			o.setQuantity(k.getQuantity());
			o.setPrice(k.getProduct().getProductPrice()*k.getQuantity());
			o.setProduct(k.getProduct());
			
			o.setOrder(order);
			//removeKartItem(k.next().getId());
			//ci.delete(k.next());
			k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
			productService.updateProduct(k.getProduct());
			orderItemService.addOrdItem(o);
		}
		
		//customer.getCart().setTotalPrice(0);
		cartItemService.reomveCartList(l);
		//l.clear();
		//log.info(customer.getUserName()+"Bought the cart");
		
	}
	
	//9.buying single item which is cart
	@PostMapping("/buyfromcart/{kartitemid}")
	public void buyFromCart(@PathVariable("kartitemid") int id) throws CartItemException
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
		removeKartItem(id);
	}

	//10.directly buying from product
	@PostMapping("/directbuy/{userid}/{productid}")
	public void buyProduct(@PathVariable("userid") String user,@PathVariable("productid") int id) throws ProductException, CustomerException
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
	}
	
	//11.getting order list
	@GetMapping("/getorders/{user}")
	public List<Order> getOrderList(@PathVariable("user") String user) throws CustomerException
	{
		//log.info(customerService.getCutomerById(user).getUserName()+"got the order list");
		return customerService.getCutomerById(user).getOrderList();
	}
	
	//12.getting item list in a order
	@GetMapping("/getorderitems/{oid}")
	public List<OrderItem> getOrderList(@PathVariable("oid") int oid) throws OrderException
	{
		//log.info("getting items of order");
		return orderService.getOrderById(oid).getOrderitemList();
	}
	
	//13.update profile
	@PutMapping("/updateprofile")
	public void updateProfile(@RequestBody Customer customer) throws CustomerException
	{
		
		Cart  cart=customerService.getCutomerById(customer.getEmailId()).getCart();
		customer.setCart(cart);
		customerService.updateCustomer(customer);
		//log.info(customer.getEmailId()+"updated the profile");
	}
	
	@GetMapping("/getpbybrand/{brand}")
	public List<Product> getProductByBrand(@PathVariable("brand")String brand)
	{
		return productService.getProductsByBrand(brand);
	}
	
}
