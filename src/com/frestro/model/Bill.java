package com.frestro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.BillDTO;

@Entity
@Table(name = "Bill")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Bill implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -213980885038606385L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "paid")
	private boolean paid;
	
	@OneToOne()
	@JoinColumn(name="orderId")
	private Orders orders;

	public Bill(){
		
	}
	
	public Bill(BillDTO billDTO){
		this.amount=billDTO.getAmount();
		this.paid=billDTO.isPaid();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

}
