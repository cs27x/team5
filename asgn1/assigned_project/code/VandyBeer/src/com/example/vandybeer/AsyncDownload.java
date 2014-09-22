package com.example.vandybeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;


	/**
	 * Asynchronously pulls data from server. Will be merged with the JSON
	 * Parsing Branch to incorporate the Jackson parsing library.
	 * 
	 * @author Chad
	 * 
	 */
	class LicenseDownloadTask extends AsyncTask<Object, Void, String> {
		
		public static final String JSON_URL = "https://data.nashville.gov/api/views/3wb6-xy3j/rows.json";
		MainActivity callerActivity;


		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Object... params) {
			
			callerActivity = (MainActivity) params[0];

			String result = "error";

			HttpClient httpclient = new DefaultHttpClient();

			HttpGet httpget = new HttpGet(JSON_URL);

			// Execute the request
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);

				Log.d("test", response.getStatusLine().toString());
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					result = convertToString(instream);
					Log.d("test", "the result is: " + result);
					instream.close();
				}
			} catch (Exception e) {
			}

			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			callerActivity.displayLicenses(result);
			
		}
	/**
	 * Helper method that converts the stream to a string. This method will no
	 * longer be needed once the JSON parsing branch is merged.
	 * 
	 * @param inputStream - The JSON stream that needs to be converted
	 * @return - a String that contains the raw JSON data
	 */
	private static String convertToString(InputStream inputStream) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

}
