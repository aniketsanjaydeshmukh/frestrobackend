package com.frestro.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.RestaurantHistoryDTO;

@Entity
@Table(name = "RestaurantHistory")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class RestaurantHistory implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2137348476586833904L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "description")
	private String description;

	@ManyToOne
	private Restaurant restaurant;

	public RestaurantHistory(){
		
	}
	
	public RestaurantHistory(RestaurantHistoryDTO dto){
		this.date = dto.getDate();
		this.description = dto.getDescription();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}
