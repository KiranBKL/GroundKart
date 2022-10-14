package com.springrest.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.springrest.exception.OrderException;
import com.springrest.exception.PaymentTypeException;
import com.springrest.model.Order;
import com.springrest.model.PaymentType;
import com.springrest.repository.PaymentTypeRepository;

@Service
public class PaymentTypeServiceImpl {
	
	Log log=LogFactory.getLog(PaymentTypeServiceImpl.class);
	
	@Autowired
	Environment env;

    @Autowired
    private  PaymentTypeRepository  paymentTypeDao;

    
	public void addPaymentType(PaymentType pt) {
		paymentTypeDao.save(pt);
	}
	
	public PaymentType getPaymentTypeById(int id) throws PaymentTypeException
	{
		if(paymentTypeDao.existsById(id))
		{
			//log.info(env.getProperty("GETORDER"));
			return paymentTypeDao.findById(id).get();
		}
		log.warn(env.getProperty("NOPT"));
		throw new PaymentTypeException(env.getProperty("NOPT"));
	}
	
	public List<PaymentType> getPaymentTypes()
	{
		return paymentTypeDao.findAll();
	}
}
