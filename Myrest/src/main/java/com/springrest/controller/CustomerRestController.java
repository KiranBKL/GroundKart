 package com.springrest.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
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
import com.springrest.model.Cart;
import com.springrest.model.Customer;
import com.springrest.service.CustomerServiceImpl;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
	
	Log log=LogFactory.getLog(CustomerRestController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	//1.registering customer
	@PostMapping("/addcustomer")
	public ResponseEntity<?>  addCustomer(@RequestBody @Valid Customer c,BindingResult br)
	{
		if(br.hasErrors())
		{
		return new ResponseEntity<String>(env.getProperty("VALIDERROR"),HttpStatus.BAD_REQUEST);
		}
		if(customerService.addCustomer(c))
		{
//			Cart cart=new Cart();
//			c.setCart(cart);
//			customerService.updateCustomer(c);
			return new ResponseEntity<String>(env.getProperty("REGISTER"),HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(env.getProperty("NOREG"),HttpStatus.CONFLICT);
		//log.info(c.getUserName()+" "+env.getProperty("REGISTER"));
	}
	
	//2.update profile
	@PutMapping("/updateprofile")
	public ResponseEntity<?> updateProfile(@RequestBody Customer customer) throws CustomerException
	{
		
//		Cart  cart=customerService.getCutomerById(customer.getEmailId()).getCart();
//		customer.setCart(cart);
		customerService.updateCustomer(customer);
		return new ResponseEntity<String>(env.getProperty("UPDATEC"),HttpStatus.ACCEPTED);

		//log.info(customer.getEmailId()+"updated the profile");
	}
	
	//3.removing user

	
	//4.getting list of customers

	

}
