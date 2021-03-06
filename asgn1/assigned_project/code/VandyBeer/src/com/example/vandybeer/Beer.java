package com.example.vandybeer;

import java.util.List;

public class Beer {
	private String mComments;
	private String mName;
	public Beer(){
		mName = "";
		mComments = "";
	}
	
	public Beer(String value){
		if(value.charAt(0) != '<'){
			mName = value;
			mComments = "";
		}
		else{
			mName = value.substring(value.indexOf("<")+1, value.indexOf("\t"));
			mComments = value.substring(value.indexOf("\t")+1);
		}
	}
	
	public String toString() {
		return "<" + this.mName + "\t" + this.mComments + ">";
	}
	
	//adds a comment about the beer
	public void setComment(String comment){
		mComments = comment;
	}
	
	//returns a list of comments about the beer
	public String getComments(){
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
