package com.frestro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.EventDao;
import com.frestro.model.Event;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("eventServices")
public class EventServicesImpl implements EventServices{

	@Autowired
	EventDao eventDao;

	@Override
	public boolean addOrUpdateEvent(Event event) {
		return eventDao.addOrUpdateEvent(event);
	}

	@Override
	public List<Event> getEventListByRestaurant(long restaurantId) {
		return eventDao.getEventListByRestaurant(restaurantId);
	}
	
}
