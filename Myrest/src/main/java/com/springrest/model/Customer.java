package com.springrest.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

//@Component
@Entity
@Table(name = "customer")
public class Customer {
	
	
	@NotEmpty
	@Pattern(regexp="^[a-z0-9]+@[a-z]+\\.[a-z]{2,3}$",message="enter the valid details")
	@Id
	String emailId;
	
	@NotEmpty
	String userName;
	
	@NotEmpty
	String password;
	
	
	int age;
	
	String address;
	
	@NotEmpty
	String phoneNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cartId")
	private Cart cart;
	
	@OneToMany(mappedBy="customer",cascade = CascadeType.ALL)
	private List<Order> orderList;

	public Customer()
	{
		
	}

	public Customer(String emailId, String userName, String password, int age, String address, String phoneNumber
			) {
		super();
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;
		this.age = age;
		this.address = address;
		this.phoneNumber = phoneNumber;
		//this.cart = cart;
	}

	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	
}
