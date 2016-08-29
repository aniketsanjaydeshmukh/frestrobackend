package com.frestro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.SpecialOfferDao;
import com.frestro.model.SpecialOffer;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("specialOfferServices")
public class SpecialOfferServicesImpl implements SpecialOfferServices{

	@Autowired
	SpecialOfferDao specialOfferDao;

	@Override
	public boolean addOrUpdateSpecialOffer(SpecialOffer specialOffer) {
		return specialOfferDao.addOrUpdateSpecialOffer(specialOffer);
	}

	@Override
	public List<SpecialOffer> getSpecialOfferByRestaurant(long restaurantId) {
		return specialOfferDao.getSpecialOfferByRestaurant(restaurantId);
	}
	
}
