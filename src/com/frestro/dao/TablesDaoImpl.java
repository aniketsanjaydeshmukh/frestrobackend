package com.frestro.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.model.Restaurant;
import com.frestro.model.Tables;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class TablesDaoImpl implements TablesDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateTable(Tables table) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(table);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public Set<Tables> getTablesByRestaurant(long restaurantId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Tables.class);
		c.createAlias("restaurant", "r");
		c.add(Restrictions.eq("r.id", restaurantId));
		
		List<Tables> list = c.list();
		Set<Tables> tablesList = new HashSet<Tables>(list); 
		tx.commit();
		session.close();
		return tablesList;
	}

	@Override
	public Tables getTablesById(long tableId) {
		Session session;
		Tables  tables = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Tables.class);
			 criteria.add(Restrictions.eq("id", tableId));
			 Object result=criteria.uniqueResult();
			 tables = (Tables)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public boolean deleteTables(long tableId) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(Tables.class, tableId);
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
	public Tables getLastTable(long restaurantId) {
		session = sessionFactory.openSession();
		Criteria c = session.createCriteria(Tables.class);
		c.createAlias("restaurant", "r");
		c.add(Restrictions.eq("r.id", restaurantId));
		c.addOrder(Order.desc("id"));
		c.setMaxResults(1);
		return (Tables)c.uniqueResult();
	}

}
