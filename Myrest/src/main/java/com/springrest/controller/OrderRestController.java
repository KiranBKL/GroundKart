package com.springrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.CustomerException;
import com.springrest.exception.OrderException;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderServiceImpl;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	OrderServiceImpl orderService;
	
	//1.getting order list
	@GetMapping("/getorders/{user}")
	public List<Order> getOrderList(@PathVariable("user") String user) throws CustomerException
	{
		//log.info(customerService.getCutomerById(user).getUserName()+"got the order list");
		return customerService.getCutomerById(user).getOrderList();
	}
	
	@GetMapping("/getorderitems/{oid}")
	public List<OrderItem> getOrderList(@PathVariable("oid") int oid) throws OrderException
	{
		//log.info("getting items of order");
		return orderService.getOrderById(oid).getOrderitemList();
	}

}
