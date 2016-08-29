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
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.OrderDishDTO;

@Entity
@Table(name = "OrderDish")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class OrderDish implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6091270477547690786L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "quantity")
	private long quantity;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="orderDish",cascade=CascadeType.ALL)
	private Set<Orders> orders;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="orderDish",cascade=CascadeType.ALL)
	private Set<Dish> dish;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="orderDish",cascade=CascadeType.ALL)
	private Set<SpecialOffer> specialOffer;
	
	public OrderDish(){
		
	}
	
	public OrderDish(OrderDishDTO orderDishDTO){
		this.quantity=orderDishDTO.getQuantity();
	}
	
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

	public Set<Orders> getOrder() {
		return orders;
	}

	public void setOrder(Set<Orders> orders) {
		this.orders = orders;
	}

	public Set<Dish> getDish() {
		return dish;
	}

	public void setDish(Set<Dish> dish) {
		this.dish = dish;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public Set<SpecialOffer> getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(Set<SpecialOffer> specialOffer) {
		this.specialOffer = specialOffer;
	}
	
	
	

}
