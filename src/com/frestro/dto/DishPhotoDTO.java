package com.frestro.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class DishPhotoDTO {

	private long id;
	
	private String url;

	private long dish;
	
	private String sessionId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getDish() {
		return dish;
	}

	public void setDish(long dish) {
		this.dish = dish;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
