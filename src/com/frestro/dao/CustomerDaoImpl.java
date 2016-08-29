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

import com.frestro.dto.CustomerDTO;
import com.frestro.model.Customer;
import com.frestro.model.Dish;
import com.frestro.model.Owner;
import com.frestro.model.Restaurant;
import com.frestro.model.VerificationToken;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class CustomerDaoImpl implements CustomerDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateCustomer(Customer customer) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(customer);
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
				Criteria c = session.createCriteria(Customer.class);
				c.add(Restrictions.eq("username", username));
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
	public boolean login(CustomerDTO customerDTO) {
		 boolean flag=true;
		    try{  	
		    session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Customer.class);
			c.add(Restrictions.eq("emailID",customerDTO.getEmailID()));
			c.add(Restrictions.eq("password", customerDTO.getPassword()));
			Customer o = (Customer)c.uniqueResult();
			if((o==null) || (o.isEnabled()==false))
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
	public Customer getCustomerByUsername(String username) {
		Session session;
		Customer  customer = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Customer.class);
			 criteria.add(Restrictions.eq("username", username));
			 Object result=criteria.uniqueResult();
			 customer = (Customer)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Customer getCustomerById(long customerId) {
		Session session;
		Customer  customer = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Customer.class);
			 criteria.add(Restrictions.eq("id", customerId));
			 Object result=criteria.uniqueResult();
			 
			 customer = (Customer)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public double getDistance(double lat1, double lon1, double lat2,
			double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}
	
	private  double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private  double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	@Override
	public List<Restaurant> getRestaurantList() {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		List<Restaurant> restaurantList = session.createQuery("FROM Restaurant").list();
		//List<Restaurant> restaurantList = session.createCriteria(Restaurant.class).list();
		tx.commit();
		session.close();
		return restaurantList;
	}

	@Override
	public boolean checkUniqueEmail(String emailId) {
		boolean flag=false;    
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Customer.class);
				c.add(Restrictions.eq("emailID", emailId));
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
				Criteria c = session.createCriteria(Customer.class);
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
	public Customer getCustomerByEmail(String emailId) {
		Session session;
		Customer  customer = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Customer.class);
			 criteria.add(Restrictions.eq("emailID", emailId));
			 Object result=criteria.uniqueResult();
			 customer = (Customer)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return customer;
	}

	
}
