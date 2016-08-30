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


@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class AmbienceDaoImpl implements AmbienceDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateAmbiene(Ambience ambience) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(ambience);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}
	@Override
	public Set<Ambience> getAmbienceByRestaurant(long restaurantId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Ambience.class);
		c.createAlias("restaurant", "r");
		c.add(Restrictions.eq("r.id", restaurantId));
		
		List<Ambience> list = c.list();
		Set<Ambience> ambienceList = new HashSet<Ambience>(list); 
		tx.commit();
		session.close();
		return ambienceList;
	}
	
	@Override
	public boolean deleteAmbience(long ambienceId) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(Ambience.class, ambienceId);
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
	public Ambience getAmbienceById(long ambienceId) {
		Session session;
		Ambience  ambience = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Ambience.class);
			 criteria.add(Restrictions.eq("id", ambienceId));
			 Object result=criteria.uniqueResult();
			 ambience = (Ambience)result;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ambience;
	}
}
