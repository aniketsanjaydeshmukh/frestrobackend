package com.frestro.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.CustomerDTO;


@Entity
@Table(name = "Customer")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Customer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7606374667370871904L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;
	
	@Column(name = "city")
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "emailID")
	private String emailID;

	@Column(name = "phoneNO")
	private long phoneNO;
	
	@Column(name = "imageData")
	private String imageData;

	@Column(name="enabled")
    private boolean enabled;
	
	@OneToOne(mappedBy="customer", cascade=CascadeType.ALL)
	private VerificationToken verificationToken;
    
	@OneToMany(fetch = FetchType.EAGER,mappedBy="customer",cascade=CascadeType.ALL)
	private Set<ResetPasswordToken> resetPasswordToken; 

	@OneToMany(fetch = FetchType.EAGER,mappedBy="customer",cascade=CascadeType.ALL)
	private Set<Orders> orders;
	
	public Set<ResetPasswordToken> getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(Set<ResetPasswordToken> resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public Customer(CustomerDTO customerDTO){
		this.username=customerDTO.getUsername();
		this.password=customerDTO.getPassword();
		this.name=customerDTO.getName();
		this.emailID=customerDTO.getEmailID();
		this.phoneNO=customerDTO.getPhoneNO();
	}
	
	public Customer(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getPhoneNO() {
		return phoneNO;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public void setPhoneNO(long phoneNO) {
		this.phoneNO = phoneNO;
	}

}
