package com.springrest.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//1
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<?>  ProductErrorException(ProductException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	//	return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
	
	//2
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<?> CustomerErrorException(CustomerException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	}
	
	//3
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<?> OrderErrorException(OrderException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	}
	
	//4
	@ExceptionHandler(CartItemException.class)
	public ResponseEntity<?> CartItemErrorException(CartItemException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	}
	
	//5
	@ExceptionHandler(OrderItemException.class)
	public ResponseEntity<?> OrderItemErrorException(OrderItemException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	}
	
	//6
	@ExceptionHandler(CartException.class)
	public ResponseEntity<?> CartErrorException(CartException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	}
	
	//7
	@ExceptionHandler(PaymentTypeException.class)
	public ResponseEntity<?> PaymentTypeErrorException(PaymentTypeException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI()),HttpStatus.NOT_FOUND);
	}
}
