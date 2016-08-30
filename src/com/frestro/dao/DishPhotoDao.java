package com.frestro.dao;

import java.util.Set;

import com.frestro.model.DishPhoto;

public interface DishPhotoDao {
	boolean addOrUpdateDishPhoto(DishPhoto dishPhoto);
	Set<DishPhoto> getDishPhotoByDish(long dishId);
	boolean deleteDishPhoto(long dishPhotoId);
	DishPhoto getDishPhotoById(long dishPhotoId);
}
