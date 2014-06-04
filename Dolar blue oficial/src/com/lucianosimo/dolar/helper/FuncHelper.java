package com.lucianosimo.dolar.helper;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class FuncHelper {

	public static InputStream retrieveStream (String url) {
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used.
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutConnection = 3000;
		int timeoutSocket = 5000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		HttpPost request = new HttpPost(url);
		try {
			HttpResponse response = client.execute(request);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responseEntity = response.getEntity();
			return responseEntity.getContent();
		} catch (IOException e) {
			request.abort();
		}
		return null;
	}
	
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
