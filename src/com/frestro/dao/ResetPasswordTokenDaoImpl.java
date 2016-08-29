package com.frestro.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.model.Dish;
import com.frestro.model.ResetPasswordToken;
import com.frestro.model.VerificationToken;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class ResetPasswordTokenDaoImpl implements ResetPasswordTokenDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean createOrUpdateResetPasswordToken(
			ResetPasswordToken resetPasswordToken) {
		boolean flag = false;
		try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(resetPasswordToken);
			flag = true;
			tx.commit();
			session.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}
	
	@Override
	public ResetPasswordToken getResetPasswordTokenByToken(String token) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(ResetPasswordToken.class);
		c.add(Restrictions.eq("token", token));
		
		ResetPasswordToken resetPasswordToken = (ResetPasswordToken)c.uniqueResult();
		tx.commit();
		session.close();
		return resetPasswordToken;
	}

	@Override
	public boolean deleteResetPasswordToken(long id) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(ResetPasswordToken.class, id);
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
