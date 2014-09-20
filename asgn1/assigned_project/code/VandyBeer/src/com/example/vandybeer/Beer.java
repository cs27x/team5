package com.example.vandybeer;

import java.util.List;

public class Beer {
	private List<BeerLocation> mLocations;
	private List<String> mComments;
	private String mName;
	Beer(){
		mName = "";
	}
	
	Beer(String name){
		mName = name;
	}
	
	public void addLocation(BeerLocation l){
		for(int i = 0; i<mLocations.size(); i++){
			if(mLocations.get(i).compareBusinessName(l) > 0){
				mLocations.add(i, l);
				return;
			}
		}
		
		mLocations.add(l);
	}
	
	public void removeLocation(BeerLocation l){
		mLocations.remove(l);
	}
	
	public void addComment(String comment){
		mComments.add(comment);
	}
	
	public List<String> getComments(){
		return mComments;
	}
	
	public String getName(){
		return mName;
	}
	
	public void setName(String name){
		mName = name;
	}
}

