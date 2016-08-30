package com.frestro.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.DishPhotoDao;
import com.frestro.model.DishPhoto;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("dishPhotoServices")
public class DishPhotoServicesImpl implements DishPhotoServices{

	@Autowired
	DishPhotoDao dishPhotoDao;
	
	@Override
	public boolean addOrUpdateDishPhoto(DishPhoto dishPhoto) {
		return dishPhotoDao.addOrUpdateDishPhoto(dishPhoto);
	}

	@Override
	public Set<DishPhoto> getDishPhotoByDish(long dishId) {
		return dishPhotoDao.getDishPhotoByDish(dishId);
	}

	@Override
	public boolean deleteDishPhoto(long dishPhotoId) {
		return dishPhotoDao.deleteDishPhoto(dishPhotoId);
	}

	@Override
	public DishPhoto getDishPhotoById(long dishPhotoId) {
		return dishPhotoDao.getDishPhotoById(dishPhotoId);
	}

}
