package com.frestro.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class OrderDishDTO {

	private long id;
	
	private long quantity;
	
	private long orderId;
	
	private long dishId;
	
	private long specialOfferId;
	
	private String sessionId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getSpecialOfferId() {
		return specialOfferId;
	}

	public void setSpecialOfferId(long specialOfferId) {
		this.specialOfferId = specialOfferId;
	}

	public long getDishId() {
		return dishId;
	}

	public void setDishId(long dishId) {
		this.dishId = dishId;
	}
}
