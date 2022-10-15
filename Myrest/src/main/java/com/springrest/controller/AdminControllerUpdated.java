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
import com.springrest.exception.OrderException;
import com.springrest.exception.ProductException;
import com.springrest.model.Customer;
import com.springrest.model.Order;
import com.springrest.model.OrderItem;
import com.springrest.model.PaymentType;
import com.springrest.model.Product;
import com.springrest.service.CustomerServiceImpl;
import com.springrest.service.OrderServiceImpl;
import com.springrest.service.PaymentTypeServiceImpl;
import com.springrest.service.ProductServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminControllerUpdated 
{
	
	
	Log log=LogFactory.getLog(ProductRestController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	ProductServiceImpl productService;
	
	@Autowired
	PaymentTypeServiceImpl paymentTypeService;
	
	//1.add the product
	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody @Valid Product p,BindingResult br)
	{
		if(br.hasErrors())
		{
		return new ResponseEntity<String>(env.getProperty("VALIDERROR"),HttpStatus.BAD_REQUEST);
		}
		//Product p=new Product();
		
		productService.addProduct(p);
		log.info("new product has been added");
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
	
	//6.add the payment type
	@PostMapping("/addpaymenttype")
	public ResponseEntity<?> addPaymentType(@RequestBody @Valid PaymentType p,BindingResult br)
	{
		if(br.hasErrors())
		{
		return new ResponseEntity<String>(env.getProperty("VALIDERROR"),HttpStatus.BAD_REQUEST);
		}
		//Product p=new Product();
		log.info("new product has been added");
		paymentTypeService.addPaymentType(p);
		return new ResponseEntity<String>(env.getProperty("PTA"),HttpStatus.CREATED);
	}
	
	//6.add the payment type
	@GetMapping("/getorders/{ostatus}")
	public ResponseEntity<?> getOrderList(@PathVariable("ostatus") String orderStatus) throws CustomerException
	{
		//log.info(customerService.getCutomerById(user).getUserName()+"got the order list");
		return new ResponseEntity<List<Order>>(orderService.getOrderListByStatus(orderStatus),HttpStatus.FOUND);

	}
	
	//7.refund
	@PostMapping("/cancelandrefund/{oid}")
	public ResponseEntity<?> refundOrder(@PathVariable("oid") int oid) throws OrderException
	{
//		//log.info("getting items of order");
//		orderService.refundOrder(oid);
//		List<OrderItem> orderItemList=orderService.getOrderById(oid).getOrderitemList();
//		for(OrderItem k:orderItemList)
//		{
//			
//			k.getProduct().setUnitStock(k.getProduct().getUnitStock()+k.getQuantity());
//			productService.updateProduct(k.getProduct());
//		}
		
		orderService.refundOrder(oid);
		return new ResponseEntity<String>(env.getProperty("OCR"),HttpStatus.ACCEPTED);

	}
	
	//8.
	@PostMapping("/acceptreturnreturn/{oid}")
	public ResponseEntity<?> acceptReturnOrder(@PathVariable("oid") int oid) throws OrderException
	{
		//log.info("getting items of order");
		orderService.acceptReturnOrder(oid);
	//	List<OrderItem> orderItemList=orderService.getOrderById(oid).getOrderitemList();
//		for(OrderItem k:orderItemList)
//		{
//			
//			k.getProduct().setUnitStock(k.getProduct().getUnitStock()+k.getQuantity());
//			productService.updateProduct(k.getProduct());
//		}
		return new ResponseEntity<String>(env.getProperty("OCR"),HttpStatus.ACCEPTED);

	}
	
	//9.
	@GetMapping("/getpaymenttypes")
	public ResponseEntity<?> getProduct()
	{
		log.info("getting the products");
		return new ResponseEntity<List<PaymentType>>(paymentTypeService.getPaymentTypes(),HttpStatus.FOUND);
	}
	
}
