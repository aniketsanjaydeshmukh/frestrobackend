package com.frestro.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.DishDTO;

@Entity
@Table(name = "Dish")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Dish implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9058390263431634397L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "ingredients")
	private String ingredients[];
	
	@Column(name = "menuType")
	private String menuType[];
	
	@Column(name = "foodType")
	private String foodType[];

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="restaurant")
	private Restaurant restaurant;
	
	@ManyToMany(mappedBy="dishes")
	private Set<SpecialOffer> specialOffers = new HashSet<SpecialOffer>();
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="orderDish")
	private OrderDish orderDish;

	public Dish(DishDTO dishDTO){
		this.name=dishDTO.getName();
		this.price=dishDTO.getPrice();
		this.ingredients=dishDTO.getIngredients();
		this.menuType=dishDTO.getMenuType();
		this.foodType=dishDTO.getFoodType();
	}
	
	public Dish(){
		
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

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}

	public String[] getMenuType() {
		return menuType;
	}

	public void setMenuType(String[] menuType) {
		this.menuType = menuType;
	}

	public String[] getFoodType() {
		return foodType;
	}

	public void setFoodType(String[] foodType) {
		this.foodType = foodType;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Set<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(Set<SpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}

	public OrderDish getOrderDish() {
		return orderDish;
	}

	public void setOrderDish(OrderDish orderDish) {
		this.orderDish = orderDish;
	}
	
}
