package com.example.vandybeer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BeerLocationInfoActivity extends Activity {
	
	BeerLocation beerlocation;
	ArrayAdapter<String> listAdapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.beerlocationinfo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		TextView businessName = (TextView)findViewById(R.id.businessName); 
		TextView businessOwner = (TextView)findViewById(R.id.businessOwner);
		TextView address = (TextView)findViewById(R.id.address);
		TextView city = (TextView)findViewById(R.id.city);
		TextView state = (TextView)findViewById(R.id.state);
		TextView zipCode = (TextView)findViewById(R.id.zipCode);
		TextView permitType = (TextView)findViewById(R.id.permitType);
		ListView listview = (ListView)findViewById(R.id.listView1);
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("BeerLocation");
		    beerlocation = new BeerLocation(value);
		    
		}
		businessName.setText(beerlocation.getBusinessName());
		businessOwner.setText(beerlocation.getBusinessOwner());
		address.setText(beerlocation.getAddress());
		city.setText(beerlocation.getCity());
		state.setText(beerlocation.getState());
		zipCode.setText("" + beerlocation.getZipCode());
		permitType.setText(beerlocation.getPermitType());
		listview.setAdapter(listAdapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beer_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

}