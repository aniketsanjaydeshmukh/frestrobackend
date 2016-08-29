package com.frestro.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.RestaurantDao;
import com.frestro.model.Restaurant;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("restaurantServices")
public class RestaurantServicesImpl implements RestaurantServices{

	@Autowired
	RestaurantDao restaurantDao;

	@Override
	public boolean addOrUpdateRestaurant(Restaurant restaurant) {
		return restaurantDao.addOrUpdateRestaurant(restaurant);
	}

	@Override
	public Restaurant getRestaurantById(long restaurantId) {
		return restaurantDao.getRestaurantById(restaurantId);
	}

	@Override
	public boolean deleteRestaurant(long restaurantId) {
		return restaurantDao.deleteRestaurant(restaurantId);
	}


	@Override
	public boolean checkUniqueEmail(String email) {
		return restaurantDao.checkUniqueEmail(email);
	}


	@Override
	public boolean checkUniquePhone(long phoneNO) {
		return restaurantDao.checkUniquePhone(phoneNO);
	}

	@Override
	public Set<Restaurant> getRestaurantByCity(String city) {
		return restaurantDao.getRestaurantByCity(city);
	}

	@Override
	public Set<Restaurant> getRestaurantByCuisine(String city,String cuisine) {
		return restaurantDao.getRestaurantByCuisine(city,cuisine);
	}

	@Override
	public Set<Restaurant> getRestaurantByArea(String city, String area) {
		return restaurantDao.getRestaurantByArea(city, area);
	}

	@Override
	public Set<Restaurant> getRestaurantByAreaCuisine(String city, String area,
			String cuisine) {
		return restaurantDao.getRestaurantByAreaCuisine(city, area, cuisine);
	}

	@Override
	public Set<String> getAreaByCity(String city) {
		return restaurantDao.getAreaByCity(city);
	}
	
	
}
