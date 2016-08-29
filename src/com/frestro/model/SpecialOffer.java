package com.frestro.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.SpecialOfferDTO;

@Entity
@Table(name = "SpecialOffer")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class SpecialOffer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5132037662407834238L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fromDate")
	private String fromDate;
	
	@Column(name = "toDate")
	private String toDate;
	
	@Column(name = "fromTime")
	private String fromTime;
	
	@Column(name = "toTime")
	private String toTime;
	

	@Column(name = "imageData")
	private String imageData;
	
	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="restaurant")
	private Restaurant restaurant;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name="SpecialOffer_Dish", 
				joinColumns={@JoinColumn(name="SpecialOffer_ID")}, 
				inverseJoinColumns={@JoinColumn(name="Dish_ID")})
	private Set<Dish> dishes= new HashSet<Dish>();
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="orderDish")
	private OrderDish orderDish;

	public SpecialOffer(){
		
	}
	
	public SpecialOffer(SpecialOfferDTO speicalOfferDTO){
		this.name=speicalOfferDTO.getName();
		this.price=speicalOfferDTO.getPrice();
		this.description=speicalOfferDTO.getDescription();
		this.fromTime=speicalOfferDTO.getFromTime();
		this.toTime=speicalOfferDTO.getToTime();
		this.fromDate=speicalOfferDTO.getFromDate();
		this.toDate=speicalOfferDTO.getToDate();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
	}

	public OrderDish getOrderDish() {
		return orderDish;
	}

	public void setOrderDish(OrderDish orderDish) {
		this.orderDish = orderDish;
	}
}
