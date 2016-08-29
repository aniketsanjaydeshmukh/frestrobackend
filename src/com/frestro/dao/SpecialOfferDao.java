package com.frestro.dao;

import java.util.List;

import com.frestro.model.SpecialOffer;

public interface SpecialOfferDao {
	boolean addOrUpdateSpecialOffer(SpecialOffer specialOffer);
	List<SpecialOffer> getSpecialOfferByRestaurant(long restaurantId);
}
