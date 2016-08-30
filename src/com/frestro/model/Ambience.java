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

import com.frestro.dto.AmbienceDTO;

@Entity
@Table(name = "Ambience")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Ambience implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5872881153580211055L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "url")
	private String url;


	@ManyToOne
	private Restaurant restaurant;
	
	public Ambience(){
		
	}
	
	public Ambience(AmbienceDTO ambienceDTO){
		this.url = ambienceDTO.getUrl();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}
