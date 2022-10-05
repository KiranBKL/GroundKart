package com.springrest.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CustomerException;
import com.springrest.exception.OrderException;
import com.springrest.exception.ProductException;
import com.springrest.model.Customer;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.model.Product;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.ProductServiceImpl;



@RestController
@RequestMapping("/admin")
public class AdminRestController {

	Log log=LogFactory.getLog(ProductRestController.class);
	
	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	ProductServiceImpl productService;
	
	//1.adding product
	@PostMapping("/addproduct")
	public void addProduct(@RequestBody Product p)
	{
		//Product p=new Product();
		log.info("new product has been added");
		productService.addProduct(p);
	}
	
	//2.getting list of customers
	@GetMapping("/getcustomers")
	public List<Customer> getCust()
	{
		log.info("getting the customers");
		return customerService.getCutomers();
	}
	
	//3.getting list of products
	@GetMapping("/getproducts")
	public List<Product> getProduct()
	{
		log.info("getting the products");
		return productService.getAllProducts();
	}
	
	//4.Deleting the product
	@DeleteMapping("deleteproduct/{id}")
	public void deleteProduct(@PathVariable("id") int id) throws ProductException
	{
		log.info("removing the product");
		productService.deleteProduct(id);
	}
	
	//5.getting a product
	@GetMapping("/getproduct/{pid}")
	public Product getProduct(@PathVariable("pid") int id) throws ProductException 
	{
		
	
		
		
			return productService.getProductById(id);
		
		
		
	}
	
	//6.update the product
	@PutMapping("/updateproduct")
	public void updateProduct(@RequestBody Product p)
	{
		log.info("updating the product");
		productService.updateProduct(p);
	}
	
	//7.getting list of orders
	@GetMapping("getorders/{user}")
	public List<Order> getOrderList(@PathVariable("user") String user) throws CustomerException
	{
		log.info("getting the orders of particular user");
		return customerService.getCutomerById(user).getOrderList();
	}
	
	//8.getting list of items in orders
	@GetMapping("getorderitems/{oid}")
	public List<OrderItem> getOrderList(@PathVariable("oid") int oid) throws OrderException
	{
		log.info("getting the items of order");
		return orderService.getOrderById(oid).getOrderitemList();
	}
	
	//9.removing user
	@DeleteMapping("/removeuser/{userid}")
	public void removeCustomer(@PathVariable("userid") String custId) throws CustomerException
	{
		log.info("removing the user");
		customerService.removeCustomer(custId);
	}
}
