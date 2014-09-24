package com.example.vandybeer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private ArrayList<BeerLocation> beerLocationList;
	ArrayAdapter<String> listAdapter;
	ListView listView;
	ProgressDialog dialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		beerLocationList = new ArrayList<BeerLocation>();
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		//listAdapter.add("This is a test");
		listView = (ListView)findViewById(R.id.listView1); 
		
		// ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(getApplicationContext(), BeerLocationInfoActivity.class);
				intent.putExtra("BeerLocation", beerLocationList.get(position).toString());
				startActivity(intent);
			}
		}); 
		dialog = ProgressDialog.show(MainActivity.this, "", "Getting Beer Locations", true);
		dialog.show();
		LicenseDownloadTask downloadTask = new LicenseDownloadTask();
		downloadTask.execute(this);
		
		
	}
	
	public void displayLicenses(ArrayList<BeerLocation> locations){
		beerLocationList = locations;
		Log.i("before for loop", "hello");
		for(BeerLocation loc : beerLocationList){
			listAdapter.add(loc.getBusinessName());
		}
		listView.setAdapter(listAdapter);
		Log.i("after for loop","bleh");
		//listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		//listAdapter.add(licenseString.substring(0, 100));
		//listView = (ListView)findViewById(R.id.listView1); 
		
		//listView.setAdapter(listAdapter);
		// ListView Item Click Listener
       /* listView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(getApplicationContext(), BeerLocationInfoActivity.class);

				startActivity(intent);
			}
		});*/
		dialog.dismiss();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void SortAlpha(ArrayList<BeerLocation> list){
		Collections.sort(list, new Comparator<BeerLocation>() { //uses the Collections class to sort it
			@Override
			public int compare(BeerLocation loc, BeerLocation loc2){  //also makes an anonymous implementation of comparator
				return loc.compareBusinessName(loc2); 
			}
		});
	} //sortAlpha
	
	private ArrayList<BeerLocation> filterPermit(ArrayList<BeerLocation> list, String permitType){
		ArrayList<BeerLocation> retList = new ArrayList<BeerLocation>();
		for(BeerLocation b : list){
			if(b.getPermitType().equals(permitType) || b.getPermitType().equals("ON/OFF-SALE BEER"))
				retList.add(b);
		}
		return retList;
	}
	
	private ArrayList<BeerLocation> sortDist(ArrayList<BeerLocation> list, double userRadius){
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(myLocation.equals(null)){
			Toast.makeText(getApplicationContext(), "Current Location Not Available", Toast.LENGTH_LONG).show();
			return list;
		}
		double latitude = myLocation.getLatitude();
		double longitude = myLocation.getLongitude();
		
		return distance(list, latitude, longitude, userRadius);
	}
	
	// -	sort by closest places to current location (need GPS location of some sort).
	// -	testing all functions
	// - 	for me: jacoco
	// - 	figure out the wrapper for JSON if you think we need it.
	// Right now BeerLocation obj seems useful for now. 
	
	// Assuming we'll get the lat/lon from some GPS android plugin in terms of doubles
	// This assumes that South lats are negative, and east longitudes are positive
	// This distance function, and deg2rad/rad2deg are taken from geodatasource.com/
	// Assumes userRadius is in miles, can be changed to be kilometers if needed
	public ArrayList<BeerLocation> distance(List<BeerLocation> list, double lat1, double lon1, double userRadius){
		
		ArrayList<BeerLocation> retList = new ArrayList<BeerLocation>();
		for(BeerLocation b : list){
			
			double dist = calculateDist(lat1, lon1, b.getLatitude(), b.getLongitude()); //calculates distance
			b.setDistance(dist); //assigns it to the BeerLocations
			
			if(dist < userRadius){  //filters by distance given
				if(retList.isEmpty()){ //makes a first element
					retList.add(b);
				}
				else{
					insertSorted(retList, b); //maintains sorted list without extra iteration
				}
			} //if(dist<radius)
		} //for BeerLocation b
		return retList;
	}
	
	//helper function that inserts BeerLocations into list in sorted order by distance
	public void insertSorted(ArrayList<BeerLocation> list, BeerLocation b){
		for(int i = 0; i<list.size(); i++){ //this is to maintain a sorted list
			if(b.getDistance() < list.get(i).getDistance()){
				list.add(i, b);
				return;
			} //if
		} //for
		list.add(b);
	}
	
	//calculates distance between ordered pairs of latitude and longitude by the haversine formula
	public double calculateDist(double lat1, double lon1, double lat2, double lon2){
		double dLon = deg2rad(lon2-lon1);
		double dLat = deg2rad(lat2-lat1);
		double a = Math.pow(Math.sin(dLat/2), 2)+ Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.pow(Math.sin(dLon/2), 2);
		double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double EarthRadius = 3959; //miles
		double dist = EarthRadius * c;
		//Haversine formula outlined at http://andrew.hedges.name/experiments/haversine/
		
		return dist;
	}
				
	// Helper functions for distance
	public double deg2rad(double deg){
		return deg*Math.PI/180;
	}
	public double rad2deg(double rad){
		return(rad * 180/Math.PI);
	}
}