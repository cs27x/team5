package com.example.vandybeer;
import java.util.HashMap;
import java.util.Map;

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
	// Storing the comments
	private Map<String, String> comments = new HashMap<String,String>(); //<k,v> is <beer name, beer comment>
	
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
		
	}
	public String toString() {
		return this.businessName + " / " + this.businessOwner + " / " + this.address + " / " + this.state + ", " + this.city + " " + this.zipCode + " (" + this.latitude + " ," + this.longitude + ") ";
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
	
	// Get beer comment by name
	public String getComment(String beerName){
		String com = "No Comment.";
		if(comments.containsKey(beerName))
		{
			com = (String)comments.get(beerName);
		}
		return com;
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
	
	// Set comment for given beer
	public void setComment(String nBeer, String nComment){
		comments.put(nBeer,nComment);
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
