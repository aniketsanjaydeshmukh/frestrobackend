package com.frestro.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.model.Event;
import com.frestro.model.SpecialOffer;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class EventDaoImpl implements EventDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateEvent(Event event) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(event);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}
	
	@Override
	public List<Event> getEventListByRestaurant(long restaurantId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Event.class);
		c.createAlias("restaurant", "r");
		c.add(Restrictions.eq("r.id", restaurantId));
		
		List<Event> eventList = c.list();
		tx.commit();
		session.close();
		return eventList;
	}
	
}
