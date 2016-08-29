package com.frestro.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class TablesDTO {

	private long id;
	
	private long tableNo;
	
	private long capacity;
	
	private boolean occupied;

	private long restaurantId;
	
	private String sessionId;
	
	private long noOfTable;
	
	public long getId() {
		return id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	
	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public long getNoOfTable() {
		return noOfTable;
	}

	public void setNoOfTable(long noOfTable) {
		this.noOfTable = noOfTable;
	}


}
