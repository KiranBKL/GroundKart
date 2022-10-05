package com.springrest.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springrest.model.ErrorInfo;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//1
	@ExceptionHandler(ProductException.class)
	public @ResponseBody ErrorInfo ProductErrorException(ProductException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
	
	//2
	@ExceptionHandler(CustomerException.class)
	public @ResponseBody ErrorInfo CustomerErrorException(CustomerException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
	
	//3
	@ExceptionHandler(OrderException.class)
	public @ResponseBody ErrorInfo OrderErrorException(OrderException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
	
	//4
	@ExceptionHandler(CartItemException.class)
	public @ResponseBody ErrorInfo CartItemErrorException(CartItemException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
	
	//5
	@ExceptionHandler(OrderItemException.class)
	public @ResponseBody ErrorInfo OrderItemErrorException(OrderItemException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
	
	//6
	@ExceptionHandler(CartException.class)
	public @ResponseBody ErrorInfo CartErrorException(CartException e,HttpServletRequest req)
	{
		//req.getUserPrincipal()
		return new ErrorInfo(LocalDateTime.now(),e.getMessage(),req.getRequestURI());
	}
}
