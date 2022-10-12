package com.springrest.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CustomerException;
import com.springrest.model.Customer;
import com.springrest.service.CustomerServiceImpl;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	
	Log log=LogFactory.getLog(LoginRestController.class);

	@Autowired
	CustomerServiceImpl customerService;
	
//	//1.
//	@GetMapping("/getcustomers")
//	public List<Customer> getCust()
//	{
//		log.info("getting the customers");
//		return customerService.getCutomers();
//	}
	
	//1.login
	@GetMapping("/login/{email}/{pass}")
	public Customer validate(@PathVariable("email") String email,@PathVariable("pass") String pass) throws CustomerException
	{
		return customerService.validate(email,pass);
	}
	
	//2.logout
	@PostMapping("/logout/{email}")
	public String logout()
	{
		return "User has been logged out";
	}
}
