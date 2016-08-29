package com.frestro.services;

import java.util.List;

import com.frestro.model.Event;

public interface EventServices {
	boolean addOrUpdateEvent(Event event);
	List<Event> getEventListByRestaurant(long restaurantId);
}
