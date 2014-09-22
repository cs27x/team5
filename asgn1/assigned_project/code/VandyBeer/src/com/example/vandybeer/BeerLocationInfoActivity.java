package com.example.vandybeer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BeerLocationInfoActivity extends Activity {
	
	BeerLocation beerlocation;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.beerlocationinfo);
		TextView businessName = (TextView)findViewById(R.id.businessName); 
		TextView businessOwner = (TextView)findViewById(R.id.businessOwner);
		TextView address = (TextView)findViewById(R.id.address);
		TextView city = (TextView)findViewById(R.id.city);
		TextView state = (TextView)findViewById(R.id.state);
		TextView zipCode = (TextView)findViewById(R.id.zipCode);
		TextView permitType = (TextView)findViewById(R.id.permitType);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("BeerLocation");
		    beerlocation = new BeerLocation(value);
		    
		}
		/*businessName.setText(beerlocation.getBusinessName());
		businessOwner.setText(beerlocation.getBusinessOwner());
		address.setText(beerlocation.getAddress());
		city.setText(beerlocation.getCity());
		state.setText(beerlocation.getState());
		zipCode.setText(beerlocation.getZipCode());
		permitType.setText(beerlocation.getPermitType());
		*/
	}

}
