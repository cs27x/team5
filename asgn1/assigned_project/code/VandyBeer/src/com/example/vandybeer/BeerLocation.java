package com.example.vandybeer;


public class BeerLocation {
	private String businessName;
	private String businessOwner;
	private String address;
	private String state;
	private int permitType;  //0 for off-sale, 1 for on-sale, 2 for both
	private String city;
	private int zipCode;
	private double latitude, longitude;
	
	BeerLocation(){
		businessName = "";	businessOwner = ""; state = "";
		city = "";	address = "";
		permitType = 0;	zipCode = 0;
		latitude = 0; longitude = 0;
	}
	
	public String getBusinessName(){
		return businessName;
	}
	
	public String getAddress(){
		return address;
	}
	
	public int getPermitType(){
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
	
	public void setBusinessName(String name){
		businessName = name;
	}
	
	public void setAddress(String nAddress){
		address = nAddress;
	}
	
	public void setPermitType(String nPermitType){
		if(nPermitType.equals("ON-SALE BEER"))
			permitType = 1;
		else if(nPermitType.equals("OFF-SALE BEER"))
			permitType = 0;
		else
			permitType = 2;
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
