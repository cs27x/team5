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
	
	public static void main(String[] args) throws Exception {
		String BEER_PERMITS = "http://data.nashville.gov/resource/3wb6-xy3j.json";
		
		// Get contents of json as string using commons IO
		String JsonStr = IOUtils.toString(new URL(BEER_PERMITS));
		
		//System.out.println(JsonStr); I have the JsonStr, now what?
		
		// create object mapper instance
		ObjectMapper mapper = new ObjectMapper();
		
		// Used to disable feature that causes mapper to break if it encounters an unknown property
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		// Let's see what type the node is
		List<BeerLocation> nav = mapper.readValue(JsonStr, mapper.getTypeFactory().constructCollectionType(List.class,
				BeerLocation.class));
		for(BeerLocation b : nav)
		{
			System.out.println(b);
		}
		//List<BeerLocation> nav = mapper.readValue(JsonStr, List.class);
		
		System.out.println();
	}
	

}



