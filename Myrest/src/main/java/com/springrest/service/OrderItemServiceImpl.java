package com.springrest.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.model.OrderItem;
import com.springrest.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements IOrderItemService{

	Log log=LogFactory.getLog(OrderItemServiceImpl.class);
	
	@Autowired
	OrderItemRepository orderItemDao;
	
	@Override
	public void addOrdItem(OrderItem ot) {
		// TODO Auto-generated method stub
		log.info("order item has been added");
		orderItemDao.save(ot);
	}

}
