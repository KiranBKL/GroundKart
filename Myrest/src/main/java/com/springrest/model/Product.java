package com.springrest.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//@Component
@Entity
@Table(name = "product")
public class Product  {

	//private static final long serialVersionUID = 5186013952828648626L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Column(name = "category")
	private String productCategory;

	
	@NotEmpty
	@Column(name = "brand")
	private String brand;

	
	@NotEmpty(message = "Product Name is mandatory")
	@Column(name = "name")
	private String productName;

	@NotNull
	@NotNull(message = "Please provide some price")
	@Column(name = "price")
	private Double productPrice;

	@NotNull
	@Column(name = "unit")
	private Integer unitStock;
	
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<CartItem> cartItemList;
	
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<OrderItem> orderItemList;
	
//	@OneToOne(mappedBy="product")
//	private CartItem c;

	@Transient
	private MultipartFile productImage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}



	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

//	public CartItem getC() {
//		return c;
//	}
//
//	public void setC(CartItem c) {
//		this.c = c;
//	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getUnitStock() {
		return unitStock;
	}

	public void setUnitStock(int unitStock) {
		this.unitStock = unitStock;
	}

	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}
}
