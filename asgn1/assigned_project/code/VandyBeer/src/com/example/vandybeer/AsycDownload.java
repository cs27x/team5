package com.example.vandybeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Asynchronously pulls data from server. Will be merged with the JSON Parsing
 * Branch to incorporate the Jackson parsing library.
 * 
 * @author Chad
 * 
 */
class LicenseDownloadTask extends
		AsyncTask<Object, Void, ArrayList<BeerLocation>> {

	public static final String JSON_URL = "https://data.nashville.gov/api/views/3wb6-xy3j/rows.json";
	MainActivity callerActivity;

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected ArrayList<BeerLocation> doInBackground(Object... params) {

		callerActivity = (MainActivity) params[0];

		/*
		 * String result = "error";
		 * 
		 * HttpClient httpclient = new DefaultHttpClient();
		 * 
		 * HttpGet httpget = new HttpGet(JSON_URL);
		 * 
		 * // Execute the request HttpResponse response; Log.d("here",
		 * "Were here"); try { response = httpclient.execute(httpget);
		 * 
		 * Log.d("test", response.getStatusLine().toString()); HttpEntity entity
		 * = response.getEntity();
		 * 
		 * if (entity != null) { InputStream instream = entity.getContent();
		 * result = convertToString(instream); Log.d("test", "the result is: " +
		 * result); instream.close(); } } catch (Exception e) {
		 * Log.i("fail","this failed"); }
		 */
		ArrayList<BeerLocation> list = new ArrayList<BeerLocation>();
		try {
			BeerJSON beer = new BeerJSON();
			list = beer.run();
			Log.i("okay", "We're okay");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("fail", "we threw an exception");
		}
		return list;
	}

	@Override
	protected void onPostExecute(ArrayList<BeerLocation> list) {
		Log.i("onPostExecute", "bleh");
		callerActivity.displayLicenses(list);

	}
}