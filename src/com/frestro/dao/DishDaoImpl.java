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

import com.frestro.model.Dish;
import com.frestro.model.Owner;
import com.frestro.model.Restaurant;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class DishDaoImpl implements DishDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateDish(Dish dish) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dish);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public List<Dish> getDishByRestaurant(long restaurantId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Dish.class);
		c.createAlias("restaurant", "r");
		c.add(Restrictions.eq("r.id", restaurantId));
		
		List<Dish> dishList = c.list();
		tx.commit();
		session.close();
		return dishList;
	}

	@Override
	public Dish getDishById(long dishId) {
		Session session;
		Dish  dish = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Dish.class);
			 criteria.add(Restrictions.eq("id", dishId));
			 Object result=criteria.uniqueResult();
			 dish = (Dish)result;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dish;
	}

	@Override
	public boolean deleteDish(long dishId) {
			boolean flag = true;
			try{
			session = sessionFactory.openSession();
			Object o = session.load(Dish.class, dishId);
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
}
