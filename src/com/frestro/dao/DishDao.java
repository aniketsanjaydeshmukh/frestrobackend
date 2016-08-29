package com.frestro.dao;

import java.util.List;

import com.frestro.model.Dish;

public interface DishDao {
	boolean addOrUpdateDish(Dish dish);
	List<Dish> getDishByRestaurant(long restaurantId);
	Dish getDishById(long dishId);
	boolean deleteDish(long dishId);
}
