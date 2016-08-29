package com.frestro.services;

import java.util.List;

import com.frestro.model.Dish;

public interface DishServices {
	boolean addOrUpdateDish(Dish dish);
	List<Dish> getDishByRestaurant(long restaurantId);
	Dish getDishById(long dishId);
	boolean deleteDish(long dishId);
}
