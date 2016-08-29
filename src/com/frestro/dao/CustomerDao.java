package com.frestro.dao;

import java.util.List;

import com.frestro.dto.CustomerDTO;
import com.frestro.model.Customer;
import com.frestro.model.Restaurant;

public interface CustomerDao {
	boolean addOrUpdateCustomer(Customer customer);
	boolean checkUniqueUsername(String username);
	boolean checkUniqueEmail(String emailId);
	boolean checkUniquePhone(long phoneNO);
	boolean login(CustomerDTO customerDTO);
	Customer getCustomerByUsername(String username);
	Customer getCustomerByEmail(String emailId);
	Customer getCustomerById(long customerId);
	double getDistance(double lat1,double lan1,double lat2,double lan2,String unit);
	List<Restaurant> getRestaurantList();
}
