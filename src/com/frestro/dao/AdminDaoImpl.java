package com.frestro.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dto.AdminDTO;
import com.frestro.model.Admin;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class AdminDaoImpl implements AdminDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateAdmin(Admin admin) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(admin);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}
	
	@Override
	public boolean checkUniqueUsername(String username) {
		boolean flag=false;
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Admin.class);
				c.add(Restrictions.eq("username", username));
				Object o = c.uniqueResult();
				if(o==null){
					flag=true;
				}
			
		 }catch(Exception e){
		    	e.printStackTrace();
		    }
		return flag;	
	}
	
	@Override
	public boolean login(AdminDTO adminDTO) {
		boolean flag=true;
	    try{  	
	    session = sessionFactory.openSession();
		Criteria c = session.createCriteria(Admin.class);
		c.add(Restrictions.eq("username",adminDTO.getUsername()));
		c.add(Restrictions.eq("password", adminDTO.getPassword()));
		Object o = c.uniqueResult();
			if(o==null){
				flag=false;
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    session.close();
		return flag;	
	}

	@Override
	public Admin getAdminByUsername(String username) {
		Session session;
		Admin  admin = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Admin.class);
			 criteria.add(Restrictions.eq("username", username));
			 Object result=criteria.uniqueResult();
			 admin = (Admin)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return admin;
	}
}
