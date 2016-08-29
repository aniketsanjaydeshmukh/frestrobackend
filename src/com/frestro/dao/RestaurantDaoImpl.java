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

import com.frestro.model.Dish;
import com.frestro.model.Restaurant;
import com.frestro.model.SpecialOffer;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class RestaurantDaoImpl implements RestaurantDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateRestaurant(Restaurant restaurant) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(restaurant);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	
	@Override
	public Restaurant getRestaurantById(long restaurantId) {
		Session session;
		Restaurant  restaurant = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Restaurant.class);
			 criteria.add(Restrictions.eq("id", restaurantId));
			 Object result=criteria.uniqueResult();
			 restaurant = (Restaurant)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public boolean deleteRestaurant(long restaurantId) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(Restaurant.class, restaurantId);
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
	public boolean checkUniqueEmail(String email) {
		boolean flag=false;
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Restaurant.class);
				c.add(Restrictions.eq("emailID", email));
				Object o = c.uniqueResult();
				if(o==null){
					flag=true;
				}
			session.close();
		 }catch(Exception e){
		    	e.printStackTrace();
		    }
		return flag;
	}

	@Override
	public boolean checkUniquePhone(long phoneNO) {
		boolean flag=false;
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Restaurant.class);
				c.add(Restrictions.eq("phoneNO", phoneNO));
				Object o = c.uniqueResult();
				if(o==null){
					flag=true;
				}
			session.close();
		 }catch(Exception e){
		    	e.printStackTrace();
		    }
		return flag;
	}


	@Override
	public Set<Restaurant> getRestaurantByCity(String city) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Restaurant.class);
		c.add(Restrictions.eq("city", city));
		
		List<Restaurant> list =  c.list();
		Set<Restaurant> restaurantList = new HashSet<Restaurant>(list); 
		tx.commit();
		session.close();
		return restaurantList;
	}


	@Override
	public Set<Restaurant> getRestaurantByCuisine(String city, String cuisine) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Restaurant.class);
		c.add(Restrictions.eq("city", city));
		
		List<Restaurant> list =  c.list();
		Set<Restaurant> restaurantList = new HashSet<Restaurant>(list); 
		Set<Restaurant> set = new HashSet<Restaurant>();
		for (Restaurant restaurant : restaurantList) {
			if(restaurant.getCuisine() != null){
				for (int i = 0; i < restaurant.getCuisine().length; i++) {
					if(cuisine.equalsIgnoreCase(restaurant.getCuisine()[i])){
						set.add(restaurant);
					}	
				}
			}
		}
		
		tx.commit();
		session.close();
		return set;
	}


	@Override
	public Set<Restaurant> getRestaurantByArea(String city, String area) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Restaurant.class);
		c.add(Restrictions.eq("city", city));
		c.add(Restrictions.eq("area", area));
		
		List<Restaurant> list =  c.list();
		Set<Restaurant> restaurantList = new HashSet<Restaurant>(list);
		tx.commit();
		session.close();
		return restaurantList;
	}


	@Override
	public Set<Restaurant> getRestaurantByAreaCuisine(String city, String area,
			String cuisine) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Restaurant.class);
		c.add(Restrictions.eq("city", city));
		c.add(Restrictions.eq("area", area));
		
		List<Restaurant> list =  c.list();
		Set<Restaurant> restaurantList = new HashSet<Restaurant>(list); 
		Set<Restaurant> set = new HashSet<Restaurant>();
		for (Restaurant restaurant : restaurantList) {
			if(restaurant.getCuisine() != null){
				for (int i = 0; i < restaurant.getCuisine().length; i++) {
					if(cuisine.equalsIgnoreCase(restaurant.getCuisine()[i])){
						set.add(restaurant);
					}	
				}
			}
		}
		
		tx.commit();
		session.close();
		return set;
	}


	@Override
	public Set<String> getAreaByCity(String city) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Restaurant.class);
		c.add(Restrictions.eq("city", city));
		
		List<Restaurant> list =  c.list();
		Set<String> areaList = new HashSet<String>();
		Set<Restaurant> restaurantList = new HashSet<Restaurant>(list); 
		for (Restaurant restaurant : restaurantList) {
			areaList.add(restaurant.getArea());
		}
		
		tx.commit();
		session.close();
		return areaList;
	}
}
