package com.frestro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.DishDao;
import com.frestro.model.Dish;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("dishServices")
public class DishServicesImpl implements DishServices{

	@Autowired
	DishDao dishDao;

	@Override
	public boolean addOrUpdateDish(Dish dish) {
		return dishDao.addOrUpdateDish(dish);
	}

	@Override
	public List<Dish> getDishByRestaurant(long restaurantId) {
		return dishDao.getDishByRestaurant(restaurantId);
	}

	@Override
	public Dish getDishById(long dishId) {
		return dishDao.getDishById(dishId);
	}

	@Override
	public boolean deleteDish(long dishId) {
		return dishDao.deleteDish(dishId);
	}
	
	
}
