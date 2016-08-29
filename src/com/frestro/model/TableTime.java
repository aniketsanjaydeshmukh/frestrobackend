package com.frestro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "TableTime")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class TableTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4193222493477819315L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "occupiedDate")
	private String occupiedDate;
	
	@Column(name = "fromTime")
	private String fromTime;
	
	@Column(name = "toTime")
	private String toTime;
	
	@OneToOne()
	@JoinColumn(name="orderId")
	private Orders orders;

	@ManyToOne
	@JoinColumn(name="tables_id")
	private Tables tables;
	
	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOccupiedDate() {
		return occupiedDate;
	}

	public void setOccupiedDate(String occupiedDate) {
		this.occupiedDate = occupiedDate;
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

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	
	
}
