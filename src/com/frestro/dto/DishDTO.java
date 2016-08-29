package com.frestro.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class DishDTO {

	private long id;
	
	private String name;
	
	private double price;
	
	private String ingredients[];
	
	private String menuType[];
	
	private String foodType[];

	private long restaurantId;
	
	private String sessionId;

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

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
