package com.frestro.services;

import java.util.List;

import com.frestro.model.SpecialOffer;

public interface SpecialOfferServices {
	boolean addOrUpdateSpecialOffer(SpecialOffer specialOffer);
	List<SpecialOffer> getSpecialOfferByRestaurant(long restaurantId);
}
