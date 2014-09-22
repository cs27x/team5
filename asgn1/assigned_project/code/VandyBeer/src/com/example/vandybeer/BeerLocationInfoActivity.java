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
		//TODO Add all textviews
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("BeerLocation");
		    beerlocation = new BeerLocation(value);
		    businessName.setText(value);
		}
		
		//businessName.setText(beerlocation.getBusinessName());
		//TODO ADD all information in text
	}

}
