package com.frestro.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.AmbienceDao;
import com.frestro.model.Ambience;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("ambienceServices")
public class AmbienceServicesImpl implements AmbienceServices{

	@Autowired
	AmbienceDao ambienceDao;

	@Override
	public boolean addOrUpdateAmbiene(Ambience ambience) {
		return ambienceDao.addOrUpdateAmbiene(ambience);
	}

	@Override
	public Set<Ambience> getAmbienceByRestaurant(long restaurantId) {
		return ambienceDao.getAmbienceByRestaurant(restaurantId);
	}

	@Override
	public boolean deleteAmbience(long ambienceId) {
		return ambienceDao.deleteAmbience(ambienceId);
	}

	@Override
	public Ambience getAmbienceById(long ambienceId) {
		return ambienceDao.getAmbienceById(ambienceId);
	}
	
}
