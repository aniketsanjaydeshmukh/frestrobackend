package com.frestro.services;

import java.util.Set;

import com.frestro.model.Restaurant;

public interface RestaurantServices {

	boolean addOrUpdateRestaurant(Restaurant restaurant);
	boolean checkUniqueEmail(String email);
	boolean checkUniquePhone(long phoneNO);
	Restaurant getRestaurantById(long restaurantId);
	boolean deleteRestaurant(long restaurantId);
	Set<Restaurant> getRestaurantByCity(String city);
	Set<Restaurant> getRestaurantByCuisine(String city,String cuisine);
	Set<Restaurant> getRestaurantByArea(String city,String area);
	Set<Restaurant> getRestaurantByAreaCuisine(String city,String area,String cuisine);
	Set<String> getAreaByCity(String city);
}
