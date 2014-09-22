package com.example.vandybeer;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeerJSON {
	//private final JavaType eventListType = objectMapper.getTypeFactory().constructCollectionType(List.class);
	//private final BeerJSON value = objectMapper.readValue(new URL(BEER_PERMITS), BeerJSON.class);
	
	// Set JSON URL
	private static final String BEER_PERMITS = "http://data.nashville.gov/resource/3wb6-xy3j.json";
	// create object mapper instance
	private final static ObjectMapper mapper = new ObjectMapper();
	// List of closest locations given a user radius will be stored here:
	private List<BeerLocation> distances;
	
	public BeerJSON() throws Exception{
		// Used to disable feature that causes mapper to break if it encounters an unknown property
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		// Retrieve Data
		//BeerJSON be = new BeerJSON();
		
		//be.addBeerToLoc(data, "PIZZEREAL", "PBR");
		//be.addBeerToLoc(data, "PIZZEREAL", "Stella");
		//be.addBeerComm(data,"PIZZEREAL","This place is ok.");
		//be.display(data, "PIZZEREAL");
	}
	
	public ArrayList<BeerLocation> run() throws Exception{
		
		return (ArrayList<BeerLocation>) retrieveData();

	}
	
	// Retrieve data from the JSON url and throw it into a list.
	public List<BeerLocation> retrieveData() throws Exception {
		// Create list from JSON
		return mapper.readValue(new URL(BEER_PERMITS), mapper.getTypeFactory().constructCollectionType(List.class,
				BeerLocation.class));
	}
	
	// Add a beer to a location for some location in the beerLocation list
	public void addBeerToLoc(List<BeerLocation> list, String nLoc, String nBeer) throws Exception{
		for(BeerLocation b : list){
			if(b.getBusinessName() != null && b.getBusinessName().equals(nLoc)) {
				// Add beer to b object if loc found
				b.addBeer(nBeer);
				System.out.println("Added " + nBeer + " to " + nLoc);
			}
		}
	}
	
	// Remove a beer from a location for some location in the beerLocation list
	public void removeBeerfromLoc(List<BeerLocation> list, String nLoc, String nBeer) throws Exception{
		for(BeerLocation b : list){
			if(b.getBusinessName() != null && b.containsBeer(nBeer) && b.getBusinessName().equals(nLoc)) {
				// Remove beer from b object if loc & beer found
				b.removeBeer(nBeer);
				System.out.println("Removed " + nBeer + " from " + nLoc);
			}
		}
	}
	
	// don't like this function, should use setComment or something. sleep now.
	// Adds beer comment to a given business
	public void addBeerComm(List<BeerLocation> list, String nLoc, String nComment) throws Exception {
		for(BeerLocation b : list)
		{
			if(b.getBusinessName() != null && b.getBusinessName().equals(nLoc)) // see this bool a lot, might make function for it
			{
				b.setBeerComm(nComment);
				System.out.println(nLoc + ": " + nComment);
			}
		}
	}

	// Displays all info for a given location (that exists) 
	public void display(List<BeerLocation> list, String nLoc){
		for(BeerLocation b : list){
			if(b.getBusinessName() != null && b.getBusinessName().equals(nLoc)){
				System.out.println(b); // Print object first
				b.printBeer();
				b.printComments();
			}
		}
	}
	
	// TODO 
	// -	sort by closest places to current location (need GPS location of some sort).
	// -	testing all functions
	// - 	for me: jacoco
	// - 	figure out the wrapper for JSON if you think we need it.
	// Right now BeerLocation obj seems useful for now. 
	
	// Assuming we'll get the lat/lon from some GPS android plugin in terms of doubles
	// This assumes that South lats are negative, and east longitudes are positive
	// This distance function, and deg2rad/rad2deg are taken from geodatasource.com/
	// Assumes userRadius is in miles, can be changed to be kilometers if needed
	public void distance(List<BeerLocation> list, double lat1, double lon1, double userRadius){
		double lat2 = 0.0;
		double lon2 = 0.0;
		
		// get lat2/lon2 from iterating through data
		for(BeerLocation b : list){
			lat2 = b.getLatitude();
			lon2 = b.getLongitude();
			double theta = lon1 - lon2;
			double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
			dist = Math.acos(dist);
			dist = rad2deg(dist);
			dist = dist * 60 * 1.1515;
			if(dist < userRadius){
				distances.add(b);
			}
		}
	}
	
	// Helper functions for distance
	public double deg2rad(double rad){
		return (rad * 180 / Math.PI);
	}
	public double rad2deg(double deg){
		return(deg * Math.PI / 180);
	}
	
	// Print the X nearest locations from a given location
	// Will have to implement X or just show them all at once. 
	public void displaceDistance(List<BeerLocation> list, String nLoc){
		for(BeerLocation b : list){
			if(b.getBusinessName() != null && b.getBusinessName().equals(nLoc)){
				System.out.println("The nearest places to you are: ");
				System.out.println(b);
			}
		}
	}
	
	// Helper function for common location check
	public boolean businessExist(BeerLocation b, String nLoc) {
		return(b.getBusinessName() != null && b.getBusinessName().equals(nLoc));
	}
}