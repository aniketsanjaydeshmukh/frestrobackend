package com.frestro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.CustomerDao;
import com.frestro.dto.CustomerDTO;
import com.frestro.model.Customer;
import com.frestro.model.Restaurant;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("customerServices")
public class CustomerServicesImpl implements CustomerServices {

	@Autowired
	CustomerDao customerDao;
	
	@Override
	public boolean addOrUpdateCustomer(Customer customer) {
		return customerDao.addOrUpdateCustomer(customer);
	}

	@Override
	public boolean login(CustomerDTO customerDTO) {
		return customerDao.login(customerDTO);
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		return customerDao.getCustomerByUsername(username);
	}

	@Override
	public Customer getCustomerById(long customerId) {
		return customerDao.getCustomerById(customerId);
	}

	@Override
	public double getDistance(double lat1, double lan1, double lat2,
			double lan2, String unit) {
		return customerDao.getDistance(lat1, lan1, lat2, lan2, unit);
	}

	@Override
	public List<Restaurant> getRestaurantList() {
		return customerDao.getRestaurantList();
	}

	@Override
	public boolean checkUniqueUsername(String username) {
		return customerDao.checkUniqueUsername(username);
	}

	@Override
	public boolean checkUniqueEmail(String emailId) {
		return customerDao.checkUniqueEmail(emailId);
	}

	@Override
	public boolean checkUniquePhone(long phoneNO) {
		return customerDao.checkUniquePhone(phoneNO);
	}

	@Override
	public Customer getCustomerByEmail(String emailId) {
		return customerDao.getCustomerByEmail(emailId);
	}

}
