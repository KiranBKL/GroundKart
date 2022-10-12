package com.springrest.controller;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.springrest.repository.OrderItemRepository;
import com.springrest.repository.ProductRepository;
import com.springrest.service.CartItemServiceImpl;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderItemServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/product")
public class ProductRestController {
	
	@Autowired
	Environment env;
	
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
	
	Log log=LogFactory.getLog(ProductRestController.class);
	
	


	//1.Getting all products
	@GetMapping("/getproducts")
	public ResponseEntity<?> getProduct()
	{
		log.info("getting the products");
		return new ResponseEntity<List<Product>>(productService.getAllProducts(),HttpStatus.FOUND);
	}
	
	//

	
	//2.Getting product by  id
	@GetMapping("/getproduct/{pid}")
	public ResponseEntity<?>  getProduct(@PathVariable("pid") int id) throws ProductException 
	{
		return new ResponseEntity<Product>(productService.getProductById(id),HttpStatus.FOUND);
	}
	
	//

	
	//3.getting product list by brand
	@GetMapping("/getpbybrand/{brand}")
	public ResponseEntity<?> getProductByBrand(@PathVariable("brand")String brand)
	{
		return new ResponseEntity<List<Product>>(productService.getProductsByBrand(brand),HttpStatus.FOUND);
	}
	
	//4.getting product list by name
	@GetMapping("/getpbyname/{name}")
	public ResponseEntity<?> getProductByNamae(@PathVariable("name") String name)
	{
		return new ResponseEntity<List<Product>>(productService.getProductsByName(name),HttpStatus.FOUND);

	}
	
	//5.getting products of category
	@GetMapping("/getpbycategory/{category}")
	public ResponseEntity<?> getProductByCategory(@PathVariable("category")String category)
	{
		return new ResponseEntity<List<Product>>(productService.getProductsByCategory(category),HttpStatus.FOUND);

	}
	
}

