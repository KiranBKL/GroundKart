package com.springrest.service;

import com.springrest.exception.CustomerException;
import com.springrest.model.*;

public interface ICustomerService {
	boolean addCustomer(Customer customer);

	void removeCustomer(String customer) throws CustomerException;
}
