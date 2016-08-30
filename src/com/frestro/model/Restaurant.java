package com.frestro.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.frestro.dto.RestaurantDTO;

@Entity
@Table(name = "Restaurant")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Restaurant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -291786786580764878L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "emailID")
	private String emailID;

	@Column(name = "phoneNO")
	private long phoneNO;

	@Column(name = "fromTime")
	private String fromTime;

	@Column(name = "toTime")
	private String toTime;

	@Column(name = "offDays")
	private String[] offDays;

	@Column(name = "overallRating")
	private double overallRating;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "owner")
	private Owner owner; 
	
	@Column(name = "lat")
	private double lat;
	
	@Column(name = "lon")
	private double lon;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="restaurant",cascade=CascadeType.ALL)
	private Set<Dish> dishes;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="restaurant",cascade=CascadeType.ALL)
	private Set<SpecialOffer> specialOffers;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="restaurant",cascade=CascadeType.ALL)
	private Set<Event> events;

	@OneToMany(fetch = FetchType.EAGER,mappedBy="restaurant")
	private Set<Tables> tables;
	
	@OneToMany(fetch = FetchType.EAGER,targetEntity=RestaurantHistory.class,cascade=CascadeType.ALL, mappedBy="restaurant")
	private Set<RestaurantHistory> restaurantHistory;
	
	@OneToMany(fetch = FetchType.EAGER,targetEntity=Ambience.class,cascade=CascadeType.ALL, mappedBy="restaurant")
	private Set<Ambience> ambience;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "cuisine")
	private String[] cuisine;
	
	@Column(name = "area")
	private String area;
	
	public Restaurant(RestaurantDTO restaurantDTO){
		this.name=restaurantDTO.getName();
		this.address=restaurantDTO.getAddress();
		this.emailID=restaurantDTO.getEmailID();
		this.phoneNO=restaurantDTO.getPhoneNO();
		this.fromTime=restaurantDTO.getFromTime();
		this.toTime=restaurantDTO.getToTime();
		this.offDays=restaurantDTO.getOffDays();
		this.overallRating=restaurantDTO.getOverallRating();
		this.lat=restaurantDTO.getLat();
		this.lon=restaurantDTO.getLon();
		this.city=restaurantDTO.getCity();
		this.area=restaurantDTO.getArea();
		this.cuisine=restaurantDTO.getCuisine();
	}
	
	public Restaurant(){
		
	}
	
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

	public Set<Tables> getTables() {
		return tables;
	}

	public void setTables(Set<Tables> tables) {
		this.tables = tables;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public long getPhoneNO() {
		return phoneNO;
	}

	public void setPhoneNO(long phoneNO) {
		this.phoneNO = phoneNO;
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

	public String[] getOffDays() {
		return offDays;
	}

	public void setOffDays(String[] offDays) {
		this.offDays = offDays;
	}

	public double getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}

	
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
	}


	public Set<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(Set<SpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String[] getCuisine() {
		return cuisine;
	}

	public void setCuisine(String[] cuisine) {
		this.cuisine = cuisine;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Set<RestaurantHistory> getRestaurantHistory() {
		return restaurantHistory;
	}

	public void setRestaurantHistory(Set<RestaurantHistory> restaurantHistory) {
		this.restaurantHistory = restaurantHistory;
	}

	public Set<Ambience> getAmbience() {
		return ambience;
	}

	public void setAmbience(Set<Ambience> ambience) {
		this.ambience = ambience;
	}
}
