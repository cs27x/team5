package com.example.vandybeer;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;

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
	
	private static final String BEER_PERMITS = "http://data.nashville.gov/resource/3wb6-xy3j.json";
	// create object mapper instance
	private final static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		
		
		// Get contents of json as string using commons IO
		String JsonStr = IOUtils.toString(new URL(BEER_PERMITS));
		
		//System.out.println(JsonStr); I have the JsonStr, now what?
		
		
		
		// Used to disable feature that causes mapper to break if it encounters an unknown property
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		// Create list from JSON
		List<BeerLocation> nav = mapper.readValue(JsonStr, mapper.getTypeFactory().constructCollectionType(List.class,
				BeerLocation.class));
		List<BeerLocation> nash = new ArrayList<BeerLocation>();
		for(BeerLocation b : nav)
		{
			//System.out.println(b.getCity());
			if(b.getCity() != null && b.getCity().equals("NASHVILLE")){ // not all beerlocations have a city
				//nash.add(b);
			}
		}
		BeerJSON be = new BeerJSON();
		List<BeerLocation> test = be.retrieveData();
		for(BeerLocation b : test) {
			//System.out.println(b);
		}
		
		be.addComment("PIZZEREAL", "PBR", "taste like dogwater");
		
		// To add comments you do the following:
		// Will add a function for this later, but this is the gist of it.
		// You pass the function name of business, beer, and the comment the user wants to add
		// Probably more efficient way that iterating through the locations, but
		// hey we're doing MVP right now. 
		for(BeerLocation b : nav)
		{
			if(b.getBusinessName() != null && b.getBusinessName().equals("PIZZEREAL"))
			{
				nash.add(b);
				b.setComment("PBR", "Tastes like you expect. Mildly terrible");
				b.setComment("bud light", "more like crud light");
				//System.out.println(b.getComment("PBR"));
				//System.out.println(b.getComment("bud light"));
			}
		}
		
		// TODO figure out the wrapper for JSON if you think we need it.
		// Right now BeerLocation obj seems useful for now. 
		
	}
	
	public List<BeerLocation> retrieveData() throws Exception {
		// Create list from JSON
		return mapper.readValue(new URL(BEER_PERMITS), mapper.getTypeFactory().constructCollectionType(List.class,
				BeerLocation.class));
	}
	
	// don't like this function, should use setComment or something. sleep now.
	public List<BeerLocation> addComment(String bizName, String beerName, String comment) throws Exception {
		List<BeerLocation> loc = retrieveData();
		List<BeerLocation> comm = new ArrayList<BeerLocation>();
		
		for(BeerLocation b : loc)
		{
			if(b.getBusinessName() != null && b.getBusinessName().equals(bizName))
			{
				comm.add(b);
				b.setComment(beerName, comment);

			}
		}
		System.out.println(beerName + " @ " + bizName + " has been noted as: " + comment);
		return comm;
	}
	

}



