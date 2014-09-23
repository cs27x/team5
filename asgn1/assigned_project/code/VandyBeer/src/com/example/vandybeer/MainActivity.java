package com.example.vandybeer;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
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
		if (id == R.id.action_search) {
			return true;
		}
		if (id == R.id.sortAZ) {
			return true;
		}
		if (id == R.id.sort_closest) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}