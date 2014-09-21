package com.example.vandybeer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeerLocation {
	private String businessName;
	private String businessOwner;
	private String address;
	private String state;
	private String permitType;  //0 for off-sale, 1 for on-sale, 2 for both
	private int permit;
	private String city;
	private int zipCode;
	private double latitude, longitude;
	// Storing the location's beers - want to store list of beers for a given location
	private Set<String> locBeers = new HashSet<String>();
	// Storing the location's beers + comments
	private Map<String, String> beerComments = new HashMap<String,String>(); //<k,v> is <beer, comment>
	
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
	
	public BeerLocation(){
		businessName = "";	businessOwner = ""; zipCode = 0;
		permitType = ""; address = ""; state = ""; city = "";
		latitude = 0.0; longitude = 0.0;
	}
	public String toString() {
		return this.businessName + " / " + this.businessOwner + " / " + this.address + " / " + this.city + ", " + this.state + " " + this.zipCode + " (" + this.latitude + " ," + this.longitude + ") ";
	}
	public String getBusinessName(){
		return businessName;
	}
	
	public String getAddress(){
		return address;
	}
	
	public int getPermitType(){
		this.setPermitType(permitType);
		return permit;
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
	public Set<String> getBeers(){
		return locBeers;
	}
	
	public void setBusinessName(String name){
		businessName = name;
	}
	
	public void setAddress(String nAddress){
		address = nAddress;
	}
	
	public void setPermitType(String nPermitType){
		if(nPermitType.equals("ON-SALE BEER"))
			permit = 1;
		else if(nPermitType.equals("OFF-SALE BEER"))
			permit = 0;
		else
			permit = 2;
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
	public void addBeer(String nBeer){
		locBeers.add(nBeer);
	}
	
	// Remove a beer from location set
	// Only remove from the set if it's not empty
	public void removeBeer(String nBeer){
		if(containsBeer(nBeer)) locBeers.remove(nBeer);
		// else do nothing, already empty
	}
	
	// Check if the beer is in the location Beer set. 
	public boolean containsBeer(String nBeer){
		boolean check = false;
		if(locBeers.contains(nBeer)) check = true;
		return check;
	}
	
	// Set comment for given beer at location
	public void setBeerComm(String nLoc, String nComment){
		beerComments.put(nLoc,nComment);
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
