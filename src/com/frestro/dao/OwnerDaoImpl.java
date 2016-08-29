package com.frestro.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dto.CustomerDTO;
import com.frestro.dto.OwnerDTO;
import com.frestro.model.Customer;
import com.frestro.model.Owner;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class OwnerDaoImpl implements OwnerDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateOwner(Owner owner) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(owner);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public boolean checkUnique(String username) {
		boolean flag=false;
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Owner.class);
			
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
	public boolean login(CustomerDTO ownerDTO) {
		 boolean flag=true;
		    try{  	
		    session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Owner.class);
			c.add(Restrictions.eq("emailID",ownerDTO.getEmailID()));
			c.add(Restrictions.eq("password", ownerDTO.getPassword()));
			Owner o = (Owner) c.uniqueResult();
			if(o==null || (o.isEnabled()==false))
			{
				flag=false;
			}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    session.close();
			return flag;	
	}

	@Override
	public Owner getOwnerByUsername(String username) {
		Session session;
		Owner  owner = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Owner.class);
			 criteria.add(Restrictions.eq("username", username));
			 Object result=criteria.uniqueResult();
			 owner = (Owner)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return owner;
	}

	@Override
	public Owner getOwnerById(long ownerId) {
		Session session;
		Owner  owner = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Owner.class);
			 criteria.add(Restrictions.eq("id", ownerId));
			 Object result=criteria.uniqueResult();
			 
			 owner = (Owner)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return owner;
	}

	@Override
	public boolean checkUniqueEmail(String emailID) {
		boolean flag=false;
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Owner.class);
			
				c.add(Restrictions.eq("emailID", emailID));
			
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
	public Owner getOwnerByEmail(String emailID) {
		Session session;
		Owner  owner = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Owner.class);
			 criteria.add(Restrictions.eq("emailID", emailID));
			 Object result=criteria.uniqueResult();
			 owner = (Owner)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return owner;
	}

	@Override
	public boolean checkUniquePhoneNO(long phoneNO) {
		boolean flag=false;    
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Owner.class);
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

}
