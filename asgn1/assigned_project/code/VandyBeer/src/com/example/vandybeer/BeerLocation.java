package com.example.vandybeer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeerLocation {
	private String businessName;
	private String businessOwner;
	private String address;
	private String state;
	private String permitType; 
	private String city;
	private int zipCode;
	private double latitude, longitude;
	// Storing the location's beers - want to store list of beers for a given location
	private List<Beer> locBeers = new ArrayList<Beer>();
	// Storing the location's beers + comments
	private String comments;
	
	// Include list of beers here instead of a set?
	
	public BeerLocation(@JsonProperty("business_name") String businessName,
			@JsonProperty("business_owner") String businessOwner,
			@JsonProperty("zip") int zipCode,
			@JsonProperty("permit_type") String permitType,
			@JsonProperty("address") String address,
			@JsonProperty("state") String state,
			@JsonProperty("city") String city,
			@JsonProperty("latitude") double latitude,
			@JsonProperty("longitude") double longitude) {
		super();
		this.businessName = businessName;
		this.businessOwner = businessOwner;
		this.zipCode = zipCode;
		this.permitType = permitType;
		this.address = address;
		this.state = state;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public BeerLocation(String beerObject){
		//TODO convert beer object from string
		String[] array = beerObject.split("\t");
		this.businessName = array[0];
		this.businessOwner = array[1];
		this.address = array[2];
		this.city = array[3];
		this.state = array[4];
		this.zipCode = Integer.parseInt(array[5]);
		this.permitType = array[6];
		this.latitude = Double.parseDouble(array[7]);
		this.longitude = Double.parseDouble(array[8]);
	}
	
	public BeerLocation(){
		businessName = "";	businessOwner = ""; zipCode = 0;
		permitType = ""; address = ""; state = ""; city = "";
		latitude = 0.0; longitude = 0.0;
	}
	
	@Override
	public String toString() {
		return this.businessName + "\t" + this.businessOwner + "\t" + this.address + "\t" + this.city + "\t" + this.state + "\t" + this.zipCode + "\t" + this.permitType + "\t" + this.latitude + "\t" + this.longitude;
	}
	
	public String getBusinessName(){
		return businessName;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPermitType(){
		return permitType;
	}
	
	public String getCity(){
		return city;
	}
	
	public int getZipCode(){
		return zipCode;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public String getMappedLocation(){
		return "(" + latitude + "," + longitude + ")";
	}
	
	public String getBusinessOwner(){
		return businessOwner;
	}
	
	public String getState(){
		return state;
	} 
	
	// Get beer list from location
	public List<Beer> getBeers(){
		return locBeers;
	}
	
	// Get comments for the location
	public String getComments(){
		return comments;
	}
	public void setBusinessName(String name){
		businessName = name;
	}
	
	public void setAddress(String nAddress){
		address = nAddress;
	}
	
	public void setPermitType(String nPermitType){
		permitType = nPermitType;
	}
	
	public void setCity(String nCity){
		city = nCity;
	}
	
	public void setZipCode(int zip){
		zipCode = zip;
	}
	
	public void setLatitude(double nLatitude){
		latitude = nLatitude;
	}
	
	public void setLongitude(double nLongitude){
		longitude = nLongitude;
	}
	
	public void setBusinessOwner(String nOwner){
		businessOwner = nOwner;
	}
	
	public void setState(String nState){
		state = nState;
	}
	
	// Add beer to location set
	public void addBeer(Beer nBeer){
		locBeers.add(nBeer);
	}
	
	// Remove a beer from location set
	// Only remove from the set if it's not empty
	public void removeBeer(int index){
		locBeers.remove(index);
		//if(containsBeer(nBeer)) locBeers.remove(nBeer);
		
		// TODO works with STRING BEER
		// else do nothing, already empty
	}
	
	// Set comment for given beer at location
	public void setLocComments(String nComment){
		comments = nComment;
	}
	
	//compares two Locations based on their business name for ease of sorting
	// Returns negative if lessThan, 0 if equal, positive if greater than
	public int compareBusinessName(BeerLocation l){
		return businessName.compareTo(l.businessName);
	}
	
	//to be implemented
	public int compareDistance(BeerLocation l){
		return 0;
	}
}