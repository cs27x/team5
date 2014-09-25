package com.example.vandybeer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private ArrayList<BeerLocation> beerLocationList;
	ArrayAdapter<String> listAdapter;
	ListView listView;
	ProgressDialog dialog;

	// settings
	boolean displayOn = true;
	boolean displayOff = true;
	boolean sortAtoZ = true;

	private ArrayList<BeerLocation> currentBeerLocationList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		beerLocationList = new ArrayList<BeerLocation>();
		currentBeerLocationList = new ArrayList<BeerLocation>();

		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		// listAdapter.add("This is a test");
		listView = (ListView) findViewById(R.id.listView1);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						BeerLocationInfoActivity.class);
				// intent.putExtra("BeerLocation",
				// beerLocationList.get(position)
				// .toString());

				intent.putExtra("BeerLocation",
						currentBeerLocationList.get(position).toString());

				startActivity(intent);
			}
		});
		dialog = ProgressDialog.show(MainActivity.this, "",
				"Getting Beer Locations", true);
		dialog.show();
		LicenseDownloadTask downloadTask = new LicenseDownloadTask();
		downloadTask.execute(this);
	}

	public void displayLicenses(ArrayList<BeerLocation> locations) {
		beerLocationList = locations;
		currentBeerLocationList = locations;

		Log.i("before for loop", "hello");
		for (BeerLocation loc : currentBeerLocationList) {
			listAdapter.add(loc.getBusinessName());
		}
		listView.setAdapter(listAdapter);
		Log.i("after for loop", "bleh");
		SortAlpha();
		// listAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1);
		// listAdapter.add(licenseString.substring(0, 100));
		// listView = (ListView)findViewById(R.id.listView1);

		// listView.setAdapter(listAdapter);
		// ListView Item Click Listener
		/*
		 * listView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View
		 * view,int position, long id) { Intent intent = new
		 * Intent(getApplicationContext(), BeerLocationInfoActivity.class);
		 * 
		 * startActivity(intent); } });
		 */
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
		if (id == R.id.action_search) {
			search();
			return true;
		}
		if (id == R.id.sortAZ) {
			SortAlpha();
			return true;
		}
		if (id == R.id.sort_closest) {
			sortDist();
			return true;
		}
		if (id == R.id.display_on_sale_beer) {
			filterLocations(true);
			return true;
		}
		if (id == R.id.display_off_sale_beer) {
			filterLocations(false);
			return true;
		}
		if (id == R.id.display_all_locations) {
			displayAll();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void SortAlpha() {
		// Collections.sort(BeerLocationList, new Comparator<BeerLocation>() {
		Collections.sort(currentBeerLocationList,
				new Comparator<BeerLocation>() {
					@Override
					public int compare(BeerLocation loc, BeerLocation loc2) {
						return loc.compareBusinessName(loc2);
					}
				});
		sortAtoZ = true;
		listAdapter.clear();
		// for (BeerLocation B : beerLocationList) {
		for (BeerLocation B : currentBeerLocationList) {
			listAdapter.add(B.getBusinessName());
		}

	} // sortAlpha

	private void filterLocations(boolean isOnSale) {

		ArrayList<BeerLocation> newList = new ArrayList<BeerLocation>();

		if (isOnSale) {
			for (BeerLocation b : beerLocationList) {
				if (b.getPermitType().toLowerCase().equals("on-sale beer")) {
					newList.add(b);
				}
			}
		} else {
			for (BeerLocation b : beerLocationList) {
				if (b.getPermitType().toLowerCase().equals("off-sale beer")) {
					newList.add(b);
				}
			}
		}
		currentBeerLocationList = newList;

		if (sortAtoZ) {
			SortAlpha();
		} else {
			sortDist();
		}

		listAdapter.clear();
		for (BeerLocation B : currentBeerLocationList) {
			listAdapter.add(B.getBusinessName());
		}

	}

	private void displayAll() {
		currentBeerLocationList = beerLocationList;
		if (sortAtoZ) {
			SortAlpha();
		} else {
			sortDist();
		}

		listAdapter.clear();
		for (BeerLocation B : currentBeerLocationList) {
			listAdapter.add(B.getBusinessName());
		}
	}

	private void sortDist() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location myLocation = lm
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (myLocation.equals(null)) {
			Toast.makeText(getApplicationContext(),
					"Current Location Not Available", Toast.LENGTH_LONG).show();
		}
		double latitude = myLocation.getLatitude();
		double longitude = myLocation.getLongitude();
		sortAtoZ = false;
		distance(latitude, longitude);
	}

	// - sort by closest places to current location (need GPS location of some
	// sort).
	// - testing all functions
	// - for me: jacoco
	// - figure out the wrapper for JSON if you think we need it.
	// Right now BeerLocation obj seems useful for now.

	// Assuming we'll get the lat/lon from some GPS android plugin in terms of
	// doubles
	// This assumes that South lats are negative, and east longitudes are
	// positive
	// This distance function, and deg2rad/rad2deg are taken from
	// geodatasource.com/
	// Assumes userRadius is in miles, can be changed to be kilometers if needed
	public void distance(double lat1, double lon1) {
		ArrayList<BeerLocation> retList = new ArrayList<BeerLocation>();
		// get lat2/lon2 from iterating through data
		// for (BeerLocation b : beerLocationList) {
		for (BeerLocation b : currentBeerLocationList) {
			double dist = calculateDist(lat1, lon1, b.getLatitude(),
					b.getLongitude());
			b.setDistance(dist);
		}
		// Collections.sort(beerLocationList, new Comparator<BeerLocation>() {
		Collections.sort(currentBeerLocationList,
				new Comparator<BeerLocation>() {
					@Override
					public int compare(BeerLocation loc, BeerLocation loc2) {
						return Double.compare(loc.getDistance(),
								loc2.getDistance());
					}
				});
		listAdapter.clear();
		// for (BeerLocation B : beerLocationList) {
		for (BeerLocation B : currentBeerLocationList) {
			listAdapter.add(B.getBusinessName());
		}
	}

	public double calculateDist(double lat1, double lon1, double lat2,
			double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		return dist;
	}

	// Helper functions for distance
	public double deg2rad(double rad) {
		return (rad * 180 / Math.PI);
	}

	public double rad2deg(double deg) {
		return (deg * Math.PI / 180);
	}

	private void search() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Search for a location");
		alert.setMessage("Location Name");

		// Set an EditText view to get user input
		final EditText editText = new EditText(this);
		alert.setView(editText);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				ArrayList<BeerLocation> newList = new ArrayList<BeerLocation>();
				String input = editText.getText().toString();

				for (BeerLocation b : beerLocationList) {
					if (b.getBusinessName().toLowerCase()
							.contains(input.toLowerCase())) {
						newList.add(b);
					}
				}
				currentBeerLocationList = newList;

				listAdapter.clear();
				for (BeerLocation B : currentBeerLocationList) {
					listAdapter.add(B.getBusinessName());
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}
}