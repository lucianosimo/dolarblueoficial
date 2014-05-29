package com.lucianosimo.dolar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucianosimo.dolar.model.SearchResponse;

public class UpdateService extends Service {

	String url = "http://loomalabs.com/servicedolar/dolar/getDolarInfo.json";
	
	@Override
	public void onStart(Intent intent, int startId) {
		if(isOnline()) {
			RemoteViews updateViews = buildUpdate(this);
			ComponentName widget = new ComponentName(this, Widget.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(widget, updateViews);
		} else {
        	Toast toast = Toast.makeText(getApplicationContext(), "No posee conexion a internet en este momento", Toast.LENGTH_LONG);
    		toast.show();
		}
	}
	
	public RemoteViews buildUpdate(Context context) {
		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		DecimalFormat format = new DecimalFormat("##.##");
    	Gson gson = new Gson();
        InputStream source = retrieveStream(url);
    	Reader reader = new InputStreamReader(source);
    	SearchResponse response = gson.fromJson(reader, SearchResponse.class);
        
        updateViews.setTextViewText(R.id.valueBuyOficial, "$" + format.format(response.getNewDolarValues().getNewOficialCompra()));
        updateViews.setTextViewText(R.id.valueSellOficial, "$" + format.format(response.getNewDolarValues().getNewOficialVenta()));
        updateViews.setTextViewText(R.id.valueBuyBlue, "$" + format.format(response.getNewDolarValues().getNewBlueCompra()));
        updateViews.setTextViewText(R.id.valueSellBlue, "$" + format.format(response.getNewDolarValues().getNewBlueVenta()));
        
        return updateViews;
	}
	
	private InputStream retrieveStream(String url) {
    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpPost getRequest = new HttpPost(url);
        try{
        	HttpResponse getResponse = client.execute(getRequest);
        	final int statusCode = getResponse.getStatusLine().getStatusCode();
        	if(statusCode != HttpStatus.SC_OK) {
        		Log.e("DolarBlueApp", "Error: Status code " + statusCode);
        		return null;
        	}
        	HttpEntity getResponseEntity = getResponse.getEntity();
        	return getResponseEntity.getContent();
        } catch (IOException e) {
        	getRequest.abort();
        	Log.e("DolarBlueApp", "Error", e);
        }
        return null;
    }
	
    public boolean isOnline() {
    	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netInfo = cm.getActiveNetworkInfo();
    	if(netInfo != null && netInfo.isConnected()) {
    		return true;
    	}
    	return false;
    }
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
