package com.example.vandybeer;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends ActionBarActivity {
    /**
     * ArrayList holding a list of beerLocation objects
     */
	private ArrayList<BeerLocation> beerLocationList;
    /**
     * listAdapter used to populate listView
     */
	ArrayAdapter<String> listAdapter;
    /**
     * listView for the Android UI
     */
	ListView listView;
    /**
     * Dialog box that tells the user when the beer locations are downloading
     */
	ProgressDialog dialog;
    /**
     * Settings for filtering
     */
	boolean displayOn = true;
	boolean displayOff = true;
	boolean sortAtoZ = true;

	private ArrayList<BeerLocation> currentBeerLocationList;

    /**
     * Called when the MainActivity is created
     * @param savedInstanceState saves the state of MainActivity when it is suspended
     */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		beerLocationList = new ArrayList<BeerLocation>();
		currentBeerLocationList = new ArrayList<BeerLocation>();

		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listView = (ListView) findViewById(R.id.listView1);

		listView.setOnItemClickListener(new OnItemClickListener() {
            /**
             * Set onItemClick to launch BeerLocationInfoActivity
             * @param parent parent view
             * @param view current view
             * @param position position in the list of BeerLocation
             * @param id id of list view
             */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						BeerLocationInfoActivity.class);
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

    /**
     * Called after the BeerLocations are downloaded. Populates Beer location list
     * and listView
     * @param locations locations parsed by JSON
     */
	public void displayLicenses(ArrayList<BeerLocation> locations) {
		beerLocationList = locations;
		currentBeerLocationList = locations;
		for (BeerLocation loc : currentBeerLocationList) {
			listAdapter.add(loc.getBusinessName());
		}
		listView.setAdapter(listAdapter);
		SortAlpha();
		dialog.dismiss();
	}

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu menu to be inflated into
     * @return true
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     * @param item item that was clicked
     * @return super.onOptionsItemSelected(item)
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

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

    /**
     * Sorts the list of beer locations AlphaNumerically
     */
	private void SortAlpha() {
		Collections.sort(currentBeerLocationList,
				new Comparator<BeerLocation>() {
					@Override
					public int compare(BeerLocation loc, BeerLocation loc2) {
						return loc.compareBusinessName(loc2);
					}
				});
		sortAtoZ = true;
		listAdapter.clear();
		for (BeerLocation B : currentBeerLocationList) {
			listAdapter.add(B.getBusinessName());
		}

	}

    /**
     * Filters the locations to distinguish bars and places to simply buy beer
     * @param isOnSale boolean that is true if the person is filtering based on places to buy beer
     *                 not bars
     */
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

    /**
     * Displays all beers without filters
     */
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

    /**
     * Sorts beers based on closest to current location
     */
	private void sortDist() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (myLocation.equals(null)) {
			Toast.makeText(getApplicationContext(),
					"Current Location Not Available", Toast.LENGTH_LONG).show();
		}
		double latitude = myLocation.getLatitude();
		double longitude = myLocation.getLongitude();
		sortAtoZ = false;
		distance(latitude, longitude);
	}

    /**
     * Calculate Distance from current location based on latitude and longitude
     * @param lat1 latitude of beer location
     * @param lon1 longitude of beer location
     */
	public void distance(double lat1, double lon1) {
		ArrayList<BeerLocation> retList = new ArrayList<BeerLocation>();
		for (BeerLocation b : currentBeerLocationList) {
			double dist = calculateDist(lat1, lon1, b.getLatitude(),
					b.getLongitude());
			b.setDistance(dist);
		}
		Collections.sort(currentBeerLocationList,
				new Comparator<BeerLocation>() {
					@Override
					public int compare(BeerLocation loc, BeerLocation loc2) {
						return Double.compare(loc.getDistance(),
								loc2.getDistance());
					}
				});
		listAdapter.clear();
		for (BeerLocation B : currentBeerLocationList) {
			listAdapter.add(B.getBusinessName());
		}
	}

    /**
     * Calculate Distance between two longitudes and latitudes
     * @param lat1 latitude of location 1
     * @param lon1 longitude of location 1
     * @param lat2 latitude of location 2
     * @param lon2 longitude of location 2
     * @return distance between the locations
     */
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

    /**
     * Changes degrees to radians
     * @param rad angle in radians
     * @return angle in degrees
     */
	private double deg2rad(double rad) {
		return (rad * 180 / Math.PI);
	}

    /**
     * Changes radians to degrees
     * @param deg angle in degrees
     * @return angle in radians
     */
	private double rad2deg(double deg) {
		return (deg * Math.PI / 180);
	}

    /**
     * Filters locations based on search criteria
     */
	private void search() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Search for a location");
		alert.setMessage("Location Name");
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
					}
				});

		alert.show();

	}
}