package com.springrest.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CustomerException;
import com.springrest.exception.ProductException;
import com.springrest.model.Customer;
import com.springrest.model.Product;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminControllerUpdated 
{
	
	
	Log log=LogFactory.getLog(ProductRestController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	ProductServiceImpl productService;
	
	//1.add the product
	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody @Valid Product p,BindingResult br)
	{
		if(br.hasErrors())
		{
		return new ResponseEntity<String>(env.getProperty("VALIDERROR"),HttpStatus.BAD_REQUEST);
		}
		//Product p=new Product();
		log.info("new product has been added");
		productService.addProduct(p);
		return new ResponseEntity<String>(env.getProperty("ADDPRODUCT"),HttpStatus.CREATED);
	}
	
	//2.getting all customers
	@GetMapping("/getcustomers")
	public ResponseEntity<?> getCust()
	{
		log.info("getting the customers");
		return new ResponseEntity<List<Customer>>(customerService.getCutomers(),HttpStatus.FOUND);

	}
	
	//3.for updating the product 
	@PutMapping("/updateproduct")
	public ResponseEntity<?> updateProduct(@RequestBody Product p)
	{
		//log.info("updating the product");
		productService.updateProduct(p);
		return new ResponseEntity<String>(env.getProperty("UPDATEP"),HttpStatus.ACCEPTED);
		
	}
	
	//4.for deleting the product
	@DeleteMapping("/deleteproduct/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) throws ProductException
	{
		log.info("removing the product");
		productService.deleteProduct(id);
		return new ResponseEntity<String>(env.getProperty("DELETEP"),HttpStatus.ACCEPTED);
	}
	
	//5.remove the user
	@DeleteMapping("/removeuser/{userid}")
	public ResponseEntity<?> removeCustomer(@PathVariable("userid") String custId) throws CustomerException
	{
		log.info("removing the user");
		customerService.removeCustomer(custId);
		return new ResponseEntity<String>(env.getProperty("REMOVEUSER"),HttpStatus.ACCEPTED);

	}
}
