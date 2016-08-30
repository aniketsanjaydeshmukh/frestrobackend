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

import com.frestro.model.Ambience;
import com.frestro.model.DishPhoto;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DishPhotoDaoImpl implements DishPhotoDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateDishPhoto(DishPhoto dishPhoto) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dishPhoto);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public Set<DishPhoto> getDishPhotoByDish(long dishId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(DishPhoto.class);
		c.createAlias("dish", "d");
		c.add(Restrictions.eq("d.id", dishId));
		
		List<DishPhoto> list = c.list();
		Set<DishPhoto> dishPhotoList = new HashSet<DishPhoto>(list); 
		tx.commit();
		session.close();
		return dishPhotoList;
	}

	@Override
	public boolean deleteDishPhoto(long dishPhotoId) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(DishPhoto.class, dishPhotoId);
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
	public DishPhoto getDishPhotoById(long dishPhotoId) {
		Session session;
		DishPhoto  dishPhtot = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(DishPhoto.class);
			 criteria.add(Restrictions.eq("id", dishPhotoId));
			 Object result=criteria.uniqueResult();
			 dishPhtot = (DishPhoto)result;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dishPhtot;
	}

}
