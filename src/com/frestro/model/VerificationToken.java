package com.frestro.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "VerificationToken")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class VerificationToken implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1845327271626207908L;

	private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
     
    private String token;
   
    @OneToOne()
    @JoinColumn(name="customerId")
	private Customer customer;
     
    @OneToOne()
    @JoinColumn(name="ownerId")
	private Owner owner;

	private Date expiryDate;
    
    @Column(name="verified")
    private boolean verified;
 
    public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
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
	public boolean isVerified() {
		return verified;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public static int getExpiration() {
		return EXPIRATION;
	}
	
	public VerificationToken() {
        super();
    }
	
    public VerificationToken(String token,boolean verified) {
        super();
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.verified = verified;
    }
     
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
     
}