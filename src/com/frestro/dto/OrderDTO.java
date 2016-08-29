package com.frestro.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class OrderDTO {

	private long id;
	
	private long orderNumber;
	
	private long tablesIds[];

	private long customerId;
	
	private String sessionId;
	
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

	public long[] getTablesIds() {
		return tablesIds;
	}

	public void setTablesIds(long[] tablesIds) {
		this.tablesIds = tablesIds;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
