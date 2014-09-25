package com.example.vandybeer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeerLocation {
    /**
     * name of the business at a location
     */
	private String businessName;
    /**
     * name of the business owner
     */
	private String businessOwner;
    /**
     * address of the business
     */
	private String address;
    /**
     * state of the business
     */
	private String state;
    /**
     * type of beer permit of the business
     */
	private String permitType;
    /**
     * city of the business
     */
	private String city;
    /**
     * zip code of the business
     */
	private int zipCode;
    /**
     * coordinates of the business
     */
	private double latitude, longitude;
    /**
     * distance from current location
     */
	private double distFromCur;
    /**
     * list of user entered beers for this business
     */
	private List<Beer> locBeers = new ArrayList<Beer>();
    /**
     * comments about this business
     */
	private String comments;

    /**
     * Constructs a beer object from a JSON string
     * @param businessName name of the business
     * @param businessOwner owner of the business
     * @param zipCode zip code of the business
     * @param permitType permit type of the business
     * @param address address of the business
     * @param state state of the business
     * @param city city of the business
     * @param latitude latitude of the business
     * @param longitude longitude of the business
     */
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
		this.distFromCur = 0.0;
		this.comments = "";
	}

    /**
     * Creates a beer location object from a string
     * @param beerObject beerLocation object as a string
     */
	public BeerLocation(String beerObject){
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
        for(int i = 9; i < array.length;  ++i){
            locBeers.add(new Beer(array[i]));
        }
		this.distFromCur = 0.0;
		this.comments = "";
	}

    /**
     * Initializes an empty BeerLocation object
     */
	public BeerLocation(){
		businessName = "";	businessOwner = ""; zipCode = 0;
		permitType = ""; address = ""; state = ""; city = "";
		latitude = 0.0; longitude = 0.0; distFromCur = 0.0; comments = "";
	}

    /**
     * Creates a string out of the BeerLocation object
     * @return object as a string
     */
	@Override
	public String toString() {
		return this.businessName + "\t" + this.businessOwner + "\t" + this.address + "\t" +
                this.city + "\t" + this.state + "\t" + this.zipCode + "\t" + this.permitType + "\t"
                + this.latitude + "\t" + this.longitude + "\t" + createBeerListString();
	}

    /**
     * Creates the list of beers as a string
     * @return list of beers as a string
     */
	private String createBeerListString(){
        String  x= "";
        for(int i = 0; i < locBeers.size(); ++i){
            x += locBeers.get(i).toString();
        }
        return x;
    }

    /**
     * @return businessName
     */
	public String getBusinessName(){
		return businessName;
	}

    /**
     * @return address
     */
	public String getAddress(){
		return address;
	}

    /**
     * @return permitType
     */
	public String getPermitType(){
		return permitType;
	}
	
	public String getCity(){
		return city;
	}

    /**
     *
     * @return zipCode
     */
	public int getZipCode(){
		return zipCode;
	}

    /**
     *
     * @return latitude
     */
	public double getLatitude(){
		return latitude;
	}

    /**
     *
     * @return longitude
     */
	public double getLongitude(){
		return longitude;
	}

    /**
     *
     * @return latitude and longitude
     */
	public String getMappedLocation(){
		return "(" + latitude + "," + longitude + ")";
	}

    /**
     *
     * @return businessOwner
     */
	public String getBusinessOwner(){
		return businessOwner;
	}

    /**
     *
     * @return state
     */
	public String getState(){
		return state;
	}

    /**
     *
     * @return distFromCur
     */
	public double getDistance(){
		return distFromCur;
	}

    /**
     *
     * @return locBeers
     */
	public List<Beer> getBeers(){
		return locBeers;
	}

    /**
     *
     * @return comments
     */
	public String getComments(){
		return comments;
	}

    /**
     *
     * @param name business name to set
     */
	public void setBusinessName(String name){
		businessName = name;
	}

    /**
     *
     * @param nAddress address to set
     */
	public void setAddress(String nAddress){
		address = nAddress;
	}

    /**
     *
     * @param nPermitType permit type to set
     */
	public void setPermitType(String nPermitType){
		permitType = nPermitType;
	}

    /**
     *
     * @param nCity city to set
     */
	public void setCity(String nCity){
		city = nCity;
	}

    /**
     *
     * @param zip zipCode to set
     */
	public void setZipCode(int zip){
		zipCode = zip;
	}

    /**
     *
     * @param nLatitude latitude to set
     */
	public void setLatitude(double nLatitude){
		latitude = nLatitude;
	}

    /**
     *
     * @param nLongitude longitude to set
     */
	public void setLongitude(double nLongitude){
		longitude = nLongitude;
	}

    /**
     *
     * @param nOwner business owner to set
     */
	public void setBusinessOwner(String nOwner){
		businessOwner = nOwner;
	}

    /**
     *
     * @param nState state to set
     */
	public void setState(String nState){
		state = nState;
	}

    /**
     *
     * @param dist distance from current location to set
     */
	public void setDistance(double dist){
		distFromCur = dist;
	}

    /**
     *
     * @param nBeer beer to be added to list
     */
	public void addBeer(Beer nBeer){
		locBeers.add(nBeer);
	}

    /**
     *
     * @param index remove a beer from the index
     */
	public void removeBeer(int index){
		locBeers.remove(index);
	}

    /**
     *
     * @param nComment comment to set for a beer
     */
	public void setLocComments(String nComment){
		comments = nComment;
	}

    /**
     * compares two Locations based on their business name for ease of sorting
     * @param l location to be compared against
     * @return negative if lessThan, 0 if equal, positive if greater than
     */
	public int compareBusinessName(BeerLocation l){
		return businessName.compareTo(l.businessName);
	}

    /**
     * compareDistance from l
     * @param l location to be compared to
     * @return 0
     */
	public int compareDistance(BeerLocation l){
		return 0;
	}
}