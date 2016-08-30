package com.frestro.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.RestaurantHistoryDao;
import com.frestro.model.RestaurantHistory;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("restaurantHistoryServices")
public class RestaurantHistoryServicesImpl implements RestaurantHistoryServices {

	@Autowired
	RestaurantHistoryDao restaurantHistoryDao; 
	
	@Override
	public boolean addOrUpdateRestaurantHistory(
			RestaurantHistory restaurantHistory) {
		return restaurantHistoryDao.addOrUpdateRestaurantHistory(restaurantHistory);
	}

	@Override
	public Set<RestaurantHistory> getRestaurantHistoryByRestaurant(
			long restaurantId) {
		return restaurantHistoryDao.getRestaurantHistoryByRestaurant(restaurantId);
	}

	@Override
	public boolean deleteRestaurantHistory(long restaurantHistoryId) {
		return restaurantHistoryDao.deleteRestaurantHistory(restaurantHistoryId);
	}

	@Override
	public RestaurantHistory getRestaurantHistoryById(long restaurantHistoryId) {
		return restaurantHistoryDao.getRestaurantHistoryById(restaurantHistoryId);
	}

}
