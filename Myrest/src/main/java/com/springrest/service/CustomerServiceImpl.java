package com.springrest.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.exception.CustomerException;
import com.springrest.model.*;
import com.springrest.repository.*;

@Service
public class CustomerServiceImpl implements ICustomerService {
    
	
	Log log=LogFactory.getLog(CustomerServiceImpl.class);
	
	
	@Autowired
	Environment env;
	String s;
	
    @Autowired
    private CustomerRepository customerDao;
    
    @PostConstruct
	public void postConstruct()
	{
		this.s=env.getProperty("NOUSER");
	}

    public boolean addCustomer(Customer customer) {  
    	if(!customerDao.existsById(customer.getEmailId()))
    	{
			Cart cart=new Cart();
			customer.setCart(cart);
			//customerService.updateCustomer(c);
    	log.info(env.getProperty("REGISTER"));
   	 	customerDao.save(customer);
   	 	return true;
    	}
    	log.info(env.getProperty("NOREG"));
    	return false;
    }

	@Override
	public void removeCustomer(String customer) throws CustomerException {
		// TODO Auto-generated method stub
		if(customerDao.existsById(customer))
		{
			log.warn(customer+" "+env.getProperty("REMOVEUSER"));
			customerDao.deleteById(customer);
			return;
		}
		log.error(s);
		throw new CustomerException(s);
	}

    public Customer getCutomerById(String id) throws CustomerException
    {
    	if(customerDao.existsById(id)) {
    		log.info(env.getProperty("GETUSER"));
    		return customerDao.findById(id).get();
    	}
    	log.error(s);
    	throw new CustomerException(s);
    }

	public List<Customer> getCutomers() {
		// TODO Auto-generated method stub
		log.info(env.getProperty("GETALLCUST"));
		return customerDao.findAll();
	}
	
	public void updateCustomer(Customer customer) throws CustomerException
	{
		Cart  cart=getCutomerById(customer.getEmailId()).getCart();
		customer.setCart(cart);
		customerDao.save(customer);
		log.info(customer.getUserName()+" "+env.getProperty("UPDATEC"));

	}

	public Customer validate(String email, String pass) throws CustomerException {
		Customer customer=getCutomerById(email);
		if(customer.getPassword().equals(pass))
		{
			log.info(customer.getUserName()+" "+env.getProperty("VALID"));
			return customer;
		}
		log.warn(env.getProperty("NOVALID"));
		throw new CustomerException(env.getProperty("NOVALID"));
	}
}

