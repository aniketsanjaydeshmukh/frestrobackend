package com.frestro.services;

import java.util.Set;

import com.frestro.model.RestaurantHistory;

public interface RestaurantHistoryServices {
	boolean addOrUpdateRestaurantHistory(RestaurantHistory restaurantHistory);
	Set<RestaurantHistory> getRestaurantHistoryByRestaurant(long restaurantId);
	boolean deleteRestaurantHistory(long restaurantHistoryId);
	RestaurantHistory getRestaurantHistoryById(long restaurantHistoryId);
}
