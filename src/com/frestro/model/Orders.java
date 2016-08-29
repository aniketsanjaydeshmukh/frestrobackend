package com.frestro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.OrderDTO;

@Entity
@Table(name = "Orders")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Orders implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598848865529554570L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "orderNumber")
	private long orderNumber;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Order_Tables", 
				joinColumns={@JoinColumn(name="Order_ID")}, 
				inverseJoinColumns={@JoinColumn(name="Tables_ID")})
	private Set<Tables> tables = new HashSet<Tables>();

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer")
	private Customer customer;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="orderDish")
	private OrderDish orderDish;
	
	@OneToOne(mappedBy="orders" , fetch = FetchType.EAGER)
	private Bill bill; 
	
	@OneToOne(mappedBy="orders" , fetch = FetchType.EAGER)
	private TableTime tableTime; 
	
	public Orders(){
		
	}
	
	public Orders(OrderDTO orderDTO){
		this.orderNumber=orderDTO.getOrderNumber();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Set<Tables> getTables() {
		return tables;
	}

	public void setTables(Set<Tables> tables) {
		this.tables = tables;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderDish getOrderDish() {
		return orderDish;
	}

	public void setOrderDish(OrderDish orderDish) {
		this.orderDish = orderDish;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public TableTime getTableTime() {
		return tableTime;
	}

	public void setTableTime(TableTime tableTime) {
		this.tableTime = tableTime;
	}

}
