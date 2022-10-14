package com.springrest.service;

import java.util.Date;
import java.util.List;

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
import com.springrest.exception.OrderException;
import com.springrest.model.Order;
import com.springrest.model.Product;
import com.springrest.repository.OrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	Log log=LogFactory.getLog(IOrderService.class);
	String s;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private OrderRepository orderDao;
	
	@PostConstruct
	public void postConstruct()
	{
		this.s=env.getProperty("NOORDER");
	}
	
	
	

	public void addOrder(Order salesOrder) {
		orderDao.save(salesOrder);
	}
	
	public Order getOrderById(int id) throws OrderException
	{
		if(orderDao.existsById(id))
		{
			log.info(env.getProperty("GETORDER"));
			return orderDao.findById(id).get();
		}
		log.warn(s);
		throw new OrderException(s);
	}




	public List<Order> getOrderListByStatus(String status) {
		// TODO Auto-generated method stub
	 	//log.info(environment.getProperty("VPBB"));
	   	 return orderDao.getProductsBybrand(status);
	}




	public void deliveryConfirm(int oid) throws OrderException {
		// TODO Auto-generated method stub
		if(orderDao.existsById(oid))
		{
			Order order=orderDao.findById(oid).get();
			if(!order.getOrderStatus().equals(env.getProperty("OD"))&&order.getOrderStatus().equals(env.getProperty("OP"))&&order.getOrderStatus().equals(env.getProperty("OPP")))
			{
				order.setOrderStatus(env.getProperty("OD"));
				orderDao.save(order);
				return;
			}
			log.warn(env.getProperty("NOC"));
			throw new OrderException(env.getProperty("NOC"));
		}
		log.warn(s);
		throw new OrderException(s);
	}




	public void cancelOrder(int oid) throws OrderException {
		// TODO Auto-generated method stub
		if(orderDao.existsById(oid))
		{
			Order order=orderDao.findById(oid).get();
			if(order.getOrderStatus().equals(env.getProperty("OP")))
			{
				order.setOrderStatus(env.getProperty("OC"));
				orderDao.save(order);
				return;
			}
			else if(order.getOrderStatus().equals(env.getProperty("OPP")))
			{
				order.setOrderStatus(env.getProperty("OCP"));
				orderDao.save(order);
				return;
			}
			log.warn(env.getProperty("NOORDERTOCANCEL"));
			throw new OrderException(env.getProperty("NOORDERTOCANCEL"));
		}
		log.warn(s);
		throw new OrderException(s);
	}




	public void refundOrder(int oid) throws OrderException {
		// TODO Auto-generated method stub
		if(orderDao.existsById(oid))
		{
			Order order=orderDao.findById(oid).get();
			if(order.getOrderStatus().equals(env.getProperty("OCP")) || order.getOrderStatus().equals(env.getProperty("OR")))
			{
				order.setOrderStatus(env.getProperty("OCR"));
				orderDao.save(order);
				return;
			}
			log.warn(env.getProperty("WREF"));
			throw new OrderException(env.getProperty("WREF"));
		
		}
		log.warn(s);
		throw new OrderException(s);
		
	}




	public void returnOrder(int oid) throws OrderException {
		// TODO Auto-generated method stub
		
		if(orderDao.existsById(oid))
		{
			Date date=new Date();
			
			Order order=orderDao.findById(oid).get();
			if(order.getDate().getDay()-date.getDay()>7)
			{
				log.error(order.getDate().getDay()-date.getDay());
				log.warn(env.getProperty("EXP"));
				throw new OrderException(env.getProperty("EXP"));
			}
			if(order.getOrderStatus().equals(env.getProperty("OD")))
			{
				order.setOrderStatus(env.getProperty("OR"));
				orderDao.save(order);
				return;
			}
			
			log.warn(env.getProperty("NR"));
			throw new OrderException(env.getProperty("NR"));
			

		}
		log.warn(s);
		throw new OrderException(s);
		
	}




	public void acceptReturnOrder(int oid) throws OrderException {
		// TODO Auto-generated method stub
		if(orderDao.existsById(oid))
		{
			Order order=orderDao.findById(oid).get();
			if(order.getOrderStatus().equals(env.getProperty("OR")))
			{
				order.setOrderStatus(env.getProperty("OCR"));
				orderDao.save(order);
				return;
			}
			
		
		}
		log.warn(s);
		throw new OrderException(s);
		
	}
  
}
