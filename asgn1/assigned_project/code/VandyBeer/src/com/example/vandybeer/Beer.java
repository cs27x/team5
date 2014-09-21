package com.example.vandybeer;

import java.util.List;

public class Beer {
	private List<BeerLocation> mLocations;
	private List<String> mComments;
	private String mName;
	public Beer(){
		mName = "";
	}
	
	public Beer(String name){
		mName = name;
	}
	
	
	//maintains sorted list of BeerLocations
	//sorts by business name
	public void addLocation(BeerLocation loc){
		if(mLocations.contains(loc))
			return;
		for(int i = 0; i<mLocations.size(); i++){
			if(mLocations.get(i).compareBusinessName(loc) > 0){ //compares business names for sorting purposes
				mLocations.add(i, loc);
				return;
			}
		}
		
		mLocations.add(loc);
	}
	
	//removes a location l
	public void removeLocation(BeerLocation l){
		mLocations.remove(l);
	}
	
	//adds a comment about the beer
	public void addComment(String comment){
		mComments.add(comment);
	}
	
	//returns a list of comments about the beer
	public List<String> getComments(){
		return mComments;
	}
	
	//returns the name of the beer
	public String getName(){
		return mName;
	}
	
	//sets the name of the beer
	public void setName(String name){
		mName = name;
	}
}

