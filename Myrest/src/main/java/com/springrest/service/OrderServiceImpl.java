package com.springrest.service;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.controller.ProductRestController;
import com.springrest.dao.OrderDao;
import com.springrest.exception.OrderException;
import com.springrest.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

	Log log=LogFactory.getLog(OrderService.class);
	String s;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private OrderDao orderDao;
	
	@PostConstruct
	public void postConstruct()
	{
		this.s=environment.getProperty("NOORDER");
	}
	
	
	

	public void addOrder(Order salesOrder) {
		orderDao.save(salesOrder);
	}
	
	public Order getOrderById(int id) throws OrderException
	{
		if(orderDao.existsById(id))
		{
			log.info(environment.getProperty("GETORDER"));
			return orderDao.findById(id).get();
		}
		log.warn(s);
		throw new OrderException(s);
	}
}
