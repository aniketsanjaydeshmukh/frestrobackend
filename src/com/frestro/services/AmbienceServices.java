package com.frestro.services;

import java.util.Set;

import com.frestro.model.Ambience;

public interface AmbienceServices {
	boolean addOrUpdateAmbiene(Ambience ambience);
	Set<Ambience> getAmbienceByRestaurant(long restaurantId);
	boolean deleteAmbience(long ambienceId);
	Ambience getAmbienceById(long ambienceId);
}
