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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springrest.controller.CartRestController;
import com.springrest.controller.ProductRestController;
import com.springrest.exception.CartItemException;
import com.springrest.exception.CustomerException;
import com.springrest.exception.OrderException;
import com.springrest.exception.PaymentTypeException;
import com.springrest.exception.ProductException;
import com.springrest.model.CartItem;
import com.springrest.model.Customer;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.model.PaymentType;
import com.springrest.model.Product;
import com.springrest.repository.OrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	Log log=LogFactory.getLog(IOrderService.class);
	String s;
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	PaymentTypeServiceImpl paymentTypeService;
	
	@Autowired
	CartRestController crc;
	
//	@Autowired
//	OrderServiceImpl orderService;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	OrderItemServiceImpl orderItemService;
	
	
	
	@Autowired
	CartItemServiceImpl cartItemService;
	
	@Autowired
	private OrderRepository orderDao;
	
	@Autowired
	ProductServiceImpl productService;
	
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
			if((order.getOrderStatus().equals(env.getProperty("OP"))||order.getOrderStatus().equals(env.getProperty("OPP"))))
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
				List<OrderItem> orderItemList=getOrderById(oid).getOrderitemList();
				for(OrderItem k:orderItemList)
				{
					
					k.getProduct().setUnitStock(k.getProduct().getUnitStock()+k.getQuantity());
					productService.updateProduct(k.getProduct());
				}
				return;
			}
			else if(order.getOrderStatus().equals(env.getProperty("OPP")))
			{
				order.setOrderStatus(env.getProperty("OCP"));
				orderDao.save(order);
				List<OrderItem> orderItemList=getOrderById(oid).getOrderitemList();
				for(OrderItem k:orderItemList)
				{
					
					k.getProduct().setUnitStock(k.getProduct().getUnitStock()+k.getQuantity());
					productService.updateProduct(k.getProduct());
				}
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
			if(order.getOrderStatus().equals(env.getProperty("OCP")))
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
				List<OrderItem> orderItemList=getOrderById(oid).getOrderitemList();
				for(OrderItem k:orderItemList)
				{
					
					k.getProduct().setUnitStock(k.getProduct().getUnitStock()+k.getQuantity());
					productService.updateProduct(k.getProduct());
				}
				return;
			}
			
		
		}
		log.warn(s);
		throw new OrderException(s);
		
	}
	
	public double buyKart(String user,int ptid) throws CustomerException, CartItemException, PaymentTypeException
	{
		Customer customer=customerService.getCutomerById(user);
		
		List<CartItem> l=customer.getCart().getCartItem();
		//System.out.println(l);
		//ListIterator<CartItem> k= l.listIterator();
		if(l.isEmpty())
		{
			log.error(env.getProperty("EMPTY"));
			throw new CartItemException(env.getProperty("EMPTY"));
		
		}

		
		Order order=new Order();
		order.setDate(new Date());
		order.setCustomer(customer);
		PaymentType paymentType=paymentTypeService.getPaymentTypeById(ptid);
		order.setpType(paymentType);
		if(ptid==1)
			order.setOrderStatus(env.getProperty("OP"));
		else
			order.setOrderStatus(env.getProperty("OPP"));
		addOrder(order);
		
		double price=0;
		for(CartItem k:l)
		{
			
			OrderItem o=new OrderItem();
			
			double oPrice=k.getProduct().getProductPrice()*k.getQuantity();
			price=price+oPrice;
			o.setQuantity(k.getQuantity());
			o.setPrice(oPrice);
			o.setProduct(k.getProduct());
			
			o.setOrder(order);
			//removeKartItem(k.next().getId());
			//ci.delete(k.next());
			k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
			productService.updateProduct(k.getProduct());
			orderItemService.addOrdItem(o);
		}
		order.setPrice(price);
		cartItemService.reomveCartList(l);
		return price;
		//customer.getCart().setTotalPrice(0);
		

	}
	
	public double buyFromKart(int id,int ptid) throws CartItemException, PaymentTypeException
	{
		CartItem cartItem=cartItemService.getCartItemById(id);
		Order order=new Order();
		order.setDate(new Date());
		order.setCustomer(cartItem.getCart().getCustomer());
		order.setPrice(cartItem.getProduct().getProductPrice()*cartItem.getQuantity());
		PaymentType paymentType=paymentTypeService.getPaymentTypeById(ptid);
		order.setpType(paymentType);
		if(ptid==1)
			order.setOrderStatus(env.getProperty("OP"));
		else
			order.setOrderStatus(env.getProperty("OPP"));
		addOrder(order);
		OrderItem orderItem=new OrderItem();
		orderItem.setPrice(cartItem.getProduct().getProductPrice()*cartItem.getQuantity());
		orderItem.setProduct(cartItem.getProduct());
		orderItem.setQuantity(cartItem.getQuantity());
		orderItem.setOrder(order);
		cartItem.getProduct().setUnitStock(cartItem.getProduct().getUnitStock()-cartItem.getQuantity());
		productService.updateProduct(cartItem.getProduct());
		orderItemService.addOrdItem(orderItem);
		//log.info(cartItem.getCart().getCustomer().getUserName()+"Bought the item from cart");
		crc.removeKartItem(id);
		return order.getPrice();
	}
	
	public double buyProduct(String user,int id,int ptid) throws ProductException, CustomerException, PaymentTypeException 
	{
		Product product=productService.getProductById(id);
		Order order=new Order();
		order.setDate(new Date());
		order.setCustomer(customerService.getCutomerById(user));
		order.setPrice(product.getProductPrice());
		PaymentType paymentType=paymentTypeService.getPaymentTypeById(ptid);
		order.setpType(paymentType);
		if(ptid==1)
			order.setOrderStatus(env.getProperty("OP"));
		else
			order.setOrderStatus(env.getProperty("OPP"));
		addOrder(order);
		addOrder(order);
		OrderItem orderItem=new OrderItem();
		orderItem.setPrice(product.getProductPrice());
		orderItem.setProduct(product);
		orderItem.setQuantity(1);
		orderItem.setOrder(order);
		//k.getProduct().setUnitStock(k.getProduct().getUnitStock()-k.getQuantity());
		product.setUnitStock(product.getUnitStock()-1);
		productService.updateProduct(product);
		orderItemService.addOrdItem(orderItem);
		return order.getPrice();
	}
  
}
