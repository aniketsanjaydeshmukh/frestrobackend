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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.TablesDTO;

@Entity
@Table(name = "Tables")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Tables implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8457307611666479070L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "tableNo")
	private long tableNo;
	
	@Column(name = "capacity")
	private long capacity;
	
	@Column(name = "occupied")
	private boolean occupied=false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="restaurant")
	private Restaurant restaurant;
	
	@ManyToMany(mappedBy="tables")
	private Set<Orders> orders = new HashSet<Orders>();
	
	@OneToMany(mappedBy="tables")
	private Set<TableTime> tableTime;
	
	public Tables(){
		
	}
	
	public Tables(TablesDTO tablesDTO){
		this.tableNo=tablesDTO.getTableNo();
		this.capacity=tablesDTO.getCapacity();
		this.occupied=tablesDTO.isOccupied();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTableNo() {
		return tableNo;
	}

	public void setTableNo(long tableNo) {
		this.tableNo = tableNo;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public Set<TableTime> getTableTime() {
		return tableTime;
	}

	public void setTableTime(Set<TableTime> tableTime) {
		this.tableTime = tableTime;
	}

}
