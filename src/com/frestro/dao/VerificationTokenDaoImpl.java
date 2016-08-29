package com.frestro.dao;



import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.model.ResetPasswordToken;
import com.frestro.model.VerificationToken;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class VerificationTokenDaoImpl implements VerificationTokenDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public
	boolean createOrUpdateVerificationToken(VerificationToken verificationToken) {
		boolean flag = false;
		try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(verificationToken);
			flag = true;
			tx.commit();
			session.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public VerificationToken getVerificationTokenByToken(String token) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(VerificationToken.class);
		c.add(Restrictions.eq("token", token));
		
		VerificationToken verificationToken = (VerificationToken)c.uniqueResult();
		tx.commit();
		session.close();
		return verificationToken;
	}

	@Override
	public boolean deleteVerificationToken(long verificationId) {
		boolean flag = true;
		try{
		session = sessionFactory.openSession();
		Object o = session.load(VerificationToken.class, verificationId);
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
