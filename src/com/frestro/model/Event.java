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

import com.frestro.dto.EventDTO;

@Entity
@Table(name = "Event")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Event implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2566339755756969537L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "toTime")
	private String toTime;
	
	@Column(name = "fromTime")
	private String fromTime;
	
	@Column(name = "toDate")
	private String toDate;
	
	@Column(name = "fromDate")
	private String fromDate;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="restaurant")
	private Restaurant restaurant;

	public Event(EventDTO eventDTO){
		this.name=eventDTO.getName();
		this.toTime=eventDTO.getToTime();
		this.fromTime=eventDTO.getFromTime();
		this.toDate=eventDTO.getToDate();
		this.fromDate=eventDTO.getFromDate();
		this.description=eventDTO.getDescription();
	}
	
	public Event(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
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
