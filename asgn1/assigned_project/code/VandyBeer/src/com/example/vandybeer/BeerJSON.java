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

import android.provider.ContactsContract.CommonDataKinds.Event;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

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
	
	public static void main(String[] args) throws Exception {
		// Used to disable feature that causes mapper to break if it encounters an unknown property
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		// Retrieve Data
		BeerJSON be = new BeerJSON();
		List<BeerLocation> data = be.retrieveData();
		for(BeerLocation b : data) {
			//System.out.println(b);
		}
		
		BeerLocation help = new BeerLocation("terrible", null, 0, null, null, null, null, 0, 0);
		System.out.println(help);
		//be.addBeer("PIZZEREAL", "PBR", "taste like dogwater");
		be.addBeerToLoc(data, "PIZZEREAL", "PBR");

		Set<String> temp = new HashSet<String>();
		for(BeerLocation b : data) {
			if(b.getBusinessName() != null && b.getBusinessName().equals("PIZZEREAL")){
				temp = b.getBeers();
				//System.out.println(b.getBeers());
			}
		}
		
		// Outputting what is in the set to make sure something is in the b locBeers value
		Iterator it = temp.iterator();
		while(it.hasNext()){
			System.out.println((String) it.next());
		}
		
		be.addBeerComm(data,"PIZZEREAL","This place is ok.");
		
		be.display(data, "PIZZEREAL");
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
	
	// TODO Display beer/comments for a given location
	// - 	figure out the wrapper for JSON if you think we need it.
	// Right now BeerLocation obj seems useful for now. 

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
}



