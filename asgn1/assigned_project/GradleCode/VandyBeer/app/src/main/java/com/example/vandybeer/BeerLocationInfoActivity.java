package com.example.vandybeer;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BeerLocationInfoActivity extends Activity {
    /**
     * Current Beer Location
     */
	BeerLocation beerlocation;
    /**
     * listAdapter used to populate listview
     */
	ArrayAdapter<String> listAdapter;
    /**
     * listview to be populated with beers
     */
    ListView listview;
    /**
     * latitude of current Beer Location
     */
	double latitude;
    /**
     * longitude of current Beer Location
     */
	double longitude;

    /**
     * Called when activity is first created
      * @param savedInstanceState last saved state
     */
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
		listview = (ListView) findViewById(R.id.listView1);
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
            /**
             * When beer is clicked allow user to enter comment
             * @param parent parent view
             * @param view current view
             * @param position clicked position
             * @param id id of list view
             */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayCommentDialog(position);
			}
		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
            /**
             * When beer is long clicked delete the beer
             * @param parent parent view
             * @param view current view
             * @param position clicked position
             * @param id id of list view
             * @return true
             */
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

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu menu  to be inflated
     * @return true
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.beer_menu, menu);
		return true;
	}

    /**
     *
     * @param item selected item
     * @return super.onOptionsItemSelected(item)
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.add_beer:
			displayBeerDialog();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

    /**
     * Create Dialog for user to enter a beer
     */
	public void displayBeerDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter a new beer");
		alert.setMessage("Name of beer");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            /**
             * When a user clicks OK add ber to list
             * @param dialog current dialog box
             * @param whichButton button that was clicked
             */
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
                    /**
                     * When a user clicks cancel then cancel the action
                     * @param dialog current dialog box
                     * @param whichButton button that was clicked
                     */
					public void onClick(DialogInterface dialog, int whichButton) {
                        //Action cancelled
					}
				});

		alert.show();
	}

    /**
     * Display comment Dialog Box
     * @param position clicked position
     */
	public void displayCommentDialog(final int position) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Your comments on this beer");
		alert.setMessage("Your comments");

		final EditText input = new EditText(this);
		alert.setView(input);
		input.setText(beerlocation.getBeers().get(position).getComments());

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            /**
             * When a user clicks OK add comment to beer list
             * @param dialog current dialog box
             * @param whichButton button that was clicked
             */
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
                    /**
                     * When a user clicks cancel then cancel the action
                     * @param dialog current dialog box
                     * @param whichButton button that was clicked
                     */
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

    /**
     * Launch Google Maps and show location of business
     * If user doesn't have GoogleMaps launch Google Maps in their Browser
     * @param view
     */
	public void showLocation(View view) {
		Intent geoIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("geo:" + latitude + "," + longitude + "?q="
						+ latitude + "," + longitude));

		if (intentChecker(geoIntent)) {
			startActivity(geoIntent);
		} else {
			Intent geoIntent2 = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?z=12&t=m&q=loc:"
							+ latitude + "+" + longitude));
			startActivity(geoIntent2);
		}
	}

    /**
     * Helper Function to launch Google Maps API
     * @param mIntent intent to be launched
     * @return false if user doesn't have GoogleMaps
     */
	public boolean intentChecker(Intent mIntent) {
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				mIntent, 0);
		return activities.size() > 0;
	}

}