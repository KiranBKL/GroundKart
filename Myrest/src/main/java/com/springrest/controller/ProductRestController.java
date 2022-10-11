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
	
	//1.
	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody Product p,BindingResult br)
	{
		if(br.hasErrors())
		{
		return new ResponseEntity<String>(env.getProperty("VALIDERROR"),HttpStatus.BAD_REQUEST);
		}
		//Product p=new Product();
		log.info("new product has been added");
		productService.addProduct(p);
		return new ResponseEntity<String>(env.getProperty("ADDPRODUCT"),HttpStatus.OK);
	}

	//2
	@GetMapping("/getproducts")
	public List<Product> getProduct()
	{
		log.info("getting the products");
		return productService.getAllProducts();
	}
	
	//3
	@DeleteMapping("deleteproduct/{id}")
	public String deleteProduct(@PathVariable("id") int id) throws ProductException
	{
		log.info("removing the product");
		productService.deleteProduct(id);
		return env.getProperty("DELETEP");
	}
	
	//4
	@GetMapping("/getproduct/{pid}")
	public Product getProduct(@PathVariable("pid") int id) throws ProductException 
	{
		return productService.getProductById(id);
	}
	
	//5
	@PutMapping("/updateproduct")
	public String updateProduct(@RequestBody Product p)
	{
		log.info("updating the product");
		productService.updateProduct(p);
		return env.getProperty("UPDATEP");
	}
	
	//6
	@GetMapping("/getpbybrand/{brand}")
	public List<Product> getProductByBrand(@PathVariable("brand")String brand)
	{
		return productService.getProductsByBrand(brand);
	}
	
	//7
	@GetMapping("/getpbyname/{name}")
	public List<Product> getProductByNamae(@PathVariable("name") String name)
	{
		return productService.getProductsByName(name);
	}
	
	//8
	@GetMapping("/getpbycategory/{category}")
	public List<Product> getProductByCategory(@PathVariable("category")String category)
	{
		return productService.getProductsByCategory(category);
	}
	
}

