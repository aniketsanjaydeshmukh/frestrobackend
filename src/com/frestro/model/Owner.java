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

import com.frestro.dto.OwnerDTO;

@Entity
@Table(name = "Owner")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Owner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3436410121369072220L;

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
	
	@Column(name = "emailID")
	private String emailID;

	@Column(name = "city")
	private String city;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phoneNO")
	private long phoneNO;
	
	@OneToOne(mappedBy="owner" , fetch = FetchType.EAGER)
	private VerificationToken verificationToken; 
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="owner",cascade=CascadeType.ALL)
	private Set<ResetPasswordToken> resetPasswordToken; 
	
	public Set<ResetPasswordToken> getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(Set<ResetPasswordToken> resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	@Column(name="enabled")
    private boolean enabled;

	public Owner(OwnerDTO ownerDTO){
		this.username = ownerDTO.getUsername();
		this.password = ownerDTO.getPassword();
		this.name = ownerDTO.getName();
		this.emailID = ownerDTO.getEmailID();
		this.city = ownerDTO.getCity();
		this.address = ownerDTO.getAddress();
		this.phoneNO = ownerDTO.getPhoneNO();
	}
	
	public Owner(){
		
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	
	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhoneNO() {
		return phoneNO;
	}

	public void setPhoneNO(long phoneNO) {
		this.phoneNO = phoneNO;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

}
