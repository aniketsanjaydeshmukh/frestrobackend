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

import com.frestro.dto.DishPhotoDTO;

@Entity
@Table(name = "DishPhoto")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class DishPhoto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679245999072389622L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "url")
	private String url;

	@ManyToOne
	private Dish dish;
	
	public DishPhoto(){
		
	}
	
	public DishPhoto(DishPhotoDTO dishPhotoDTO){
		this.url = dishPhotoDTO.getUrl();
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

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	

}
