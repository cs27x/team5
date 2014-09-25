package com.example.vandybeer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BeerJSON {

    /**
     * Constant JSON URL to use for the beer resource Data Base
     */
    private static final String BEER_PERMITS = "http://data.nashville.gov/resource/3wb6-xy3j.json";

    /**
     * Create Object Mapper
     */
    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Create a BeerJSON object
     *
     * @throws Exception
     */
    public BeerJSON() throws Exception {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * retrieves all data from JSON
     *
     * @return an ArrayList of BeerLocations
     * @throws Exception
     */
    public ArrayList<BeerLocation> run() throws Exception {

        return (ArrayList<BeerLocation>) retrieveData();

    }

    /**
     * retrieves all data from JSON
     *
     * @return a List of BeerLocations
     * @throws Exception
     */
    public List<BeerLocation> retrieveData() throws Exception {
        return MAPPER.readValue(new URL(BEER_PERMITS), MAPPER.getTypeFactory().constructCollectionType(List.class,
                BeerLocation.class));
    }
}