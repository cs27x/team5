package com.example.vandybeer;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BeerLocationInfoActivity extends Activity {

	BeerLocation beerlocation;
	ArrayAdapter<String> listAdapter;
	double latitude;
	double longitude;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beerlocationinfo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		TextView businessName = (TextView) findViewById(R.id.businessName);
		TextView businessOwner = (TextView) findViewById(R.id.businessOwner);
		TextView address = (TextView) findViewById(R.id.address);
		TextView city = (TextView) findViewById(R.id.city);
		TextView state = (TextView) findViewById(R.id.state);
		TextView zipCode = (TextView) findViewById(R.id.zipCode);
		TextView permitType = (TextView) findViewById(R.id.permitType);
		ListView listview = (ListView) findViewById(R.id.listView1);
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String value = extras.getString("BeerLocation");
			beerlocation = new BeerLocation(value);

		}
		latitude = beerlocation.getLatitude();
		longitude = beerlocation.getLongitude();

		businessName.setText(beerlocation.getBusinessName());
		businessOwner.setText(beerlocation.getBusinessOwner());
		address.setText(beerlocation.getAddress());
		city.setText(beerlocation.getCity());
		state.setText(beerlocation.getState());
		zipCode.setText("" + beerlocation.getZipCode());
		permitType.setText(beerlocation.getPermitType());
		listview.setAdapter(listAdapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayCommentDialog(position);
			}
		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				beerlocation.getBeers().remove(position);
				listAdapter.clear();
				for (Beer beer : beerlocation.getBeers()) {
					listAdapter.add(beer.getName() + " : " + beer.getComments());
				}
				return true;
			}

		});
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
		case R.id.add_beer:
			displayBeerDialog();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void displayBeerDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter a new beer");
		alert.setMessage("Name of beer");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				beerlocation.addBeer(new Beer(value));
				listAdapter.clear();
				for (Beer beer : beerlocation.getBeers()) {
					listAdapter.add(beer.getName() + " : " + beer.getComments());
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

	public void displayCommentDialog(final int position) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Your comments on this beer");
		alert.setMessage("Your comments");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);
		input.setText(beerlocation.getBeers().get(position).getComments());

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();

				beerlocation.getBeers().get(position).setComment(value);
				listAdapter.clear();
				for (Beer beer : beerlocation.getBeers()) {
					listAdapter.add(beer.getName() + " : " + beer.getComments());
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

	public void showLocation(View view) {
		// Create the intent for geoIntent capable devices
		Intent geoIntent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse("geo:" + latitude + "," + longitude + "?q="
						+ latitude + "," + longitude));

		// Check to make sure the intent will work
		if (intentChecker(geoIntent)) {
			startActivity(geoIntent);

			// If the intent won't work, use the maps.google.com API
		} else {
			Intent geoIntent2 = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?z=12&t=m&q=loc:"
							+ latitude + "+" + longitude));
			startActivity(geoIntent2);
		}
	}

	public boolean intentChecker(Intent mIntent) {
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				mIntent, 0);
		return activities.size() > 0;
	}

}