package com.example.vandybeer;

public class Beer {
    /**
     * Comments about the beer
     */
	private String my_comments;
    /**
     * Name of the beer
     */
	private String my_name;

    /**
     * Default Constructor. Constructs blank beer object
     */
	public Beer(){
		my_name = "";
		my_comments = "";
	}

    /**
     * Initializes name to value and comments to the empty string
     * @param value name of Beer
     */
	public Beer(String value){
        my_name = value;
        my_comments ="";
    }

    /**
     * converts a beer object to a string
     * @return beer object as a string
     */
	public String toString() {
		return this.my_name + "\t" + this.my_comments + "\t";
	}

    /**
     *
     * @param comment comment to be set
     */
	public void setComment(String comment){
		my_comments = comment;
	}

    /**
     *
      * @return my_comments
     */
    public String getComments(){
		return my_comments;
	}

    /**
     *
      * @return my_name
     */
    public String getName(){
		return my_name;
	}

    /**
     *
      * @param name name to be set
     */
    public void setName(String name){
		my_name = name;
	}
}
