package com.frestro.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.frestro.model.RestaurantHistory;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class RestaurantHistoryDaoImpl implements RestaurantHistoryDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateRestaurantHistory(
			RestaurantHistory restaurantHistory) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(restaurantHistory);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public Set<RestaurantHistory> getRestaurantHistoryByRestaurant(
			long restaurantId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(RestaurantHistory.class);
		c.createAlias("restaurant", "r");
		c.add(Restrictions.eq("r.id", restaurantId));
		
		List<RestaurantHistory> list = c.list();
		Set<RestaurantHistory> restaurantHistoryList = new HashSet<RestaurantHistory>(list); 
		tx.commit();
		session.close();
		return restaurantHistoryList;
	}

	@Override
	public boolean deleteRestaurantHistory(long restaurantHistoryId) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(RestaurantHistory.class, restaurantHistoryId);
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public RestaurantHistory getRestaurantHistoryById(long restaurantHistoryId) {
		Session session;
		RestaurantHistory  restaurantHistory = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(RestaurantHistory.class);
			 criteria.add(Restrictions.eq("id", restaurantHistoryId));
			 Object result=criteria.uniqueResult();
			 restaurantHistory = (RestaurantHistory)result;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return restaurantHistory;
	}

}
