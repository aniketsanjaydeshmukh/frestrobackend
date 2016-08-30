package com.frestro.services;

import java.util.Set;

import com.frestro.model.DishPhoto;

public interface DishPhotoServices {
	boolean addOrUpdateDishPhoto(DishPhoto dishPhoto);
	Set<DishPhoto> getDishPhotoByDish(long dishId);
	boolean deleteDishPhoto(long dishPhotoId);
	DishPhoto getDishPhotoById(long dishPhotoId);
}
