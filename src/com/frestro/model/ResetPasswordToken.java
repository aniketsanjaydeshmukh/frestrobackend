package com.frestro.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "ResetPasswordToken")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class ResetPasswordToken implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 3739324198989906128L;

	private static final int EXPIRATION = 60 * 24;
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
	     
	    private String token;
	   
	    @ManyToOne
	    @JoinColumn(name="customerId")
		private Customer customer;
	    
	    @ManyToOne
	    @JoinColumn(name="ownerId")
		private Owner owner;
	     
	    
		public Owner getOwner() {
			return owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}

		private Date expiryDate;
		
		 @Column(name="changed")
		 private boolean changed;
		 
		public boolean isChanged() {
			return changed;
		}

		public void setChanged(boolean changed) {
			this.changed = changed;
		}

		public static int getExpiration() {
			return EXPIRATION;
		}

		public ResetPasswordToken(){
			super();
		}
		
		public ResetPasswordToken(String token){
			super();
	        this.token = token;
	        this.expiryDate = calculateExpiryDate(EXPIRATION);
		}
		
	
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public Date getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
		}

		private Date calculateExpiryDate(int expiryTimeInMinutes) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Timestamp(cal.getTime().getTime()));
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	    }

}
