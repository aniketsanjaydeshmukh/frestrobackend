package com.frestro.dao;

import java.util.List;

import com.frestro.model.Event;

public interface EventDao {
	boolean addOrUpdateEvent(Event event);
	List<Event> getEventListByRestaurant(long restaurantId);
}
