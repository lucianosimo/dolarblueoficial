package com.lucianosimo.dolar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucianosimo.dolar.model.SearchResponse;

public class MainActivity extends Activity {

	String url = "http://loomalabs.com/servicedolar/dolar/getDolarInfo.json";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream source = retrieveStream(url);
        
        if(isOnline() && source != null) {
        	
            final TextView blueBuy = (TextView) findViewById(R.id.valueBuyBlue);
            final TextView blueSell = (TextView) findViewById(R.id.valueSellBlue);
            final TextView oficialBuy = (TextView) findViewById(R.id.valueBuyOficial);
            final TextView oficialSell = (TextView) findViewById(R.id.valueSellOficial);
            final TextView datetime = (TextView) findViewById(R.id.valueDatetime);
            final TextView card = (TextView) findViewById(R.id.valueCard);
            
        	DecimalFormat format = new DecimalFormat("##.##");
        	Gson gson = new Gson();
        	Reader reader = new InputStreamReader(source);
        	SearchResponse response = gson.fromJson(reader, SearchResponse.class);
            
            oficialBuy.setText("$" + format.format(response.getNewDolarValues().getNewOficialCompra()));
            oficialSell.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta()));
            blueBuy.setText("$" + format.format(response.getNewDolarValues().getNewBlueCompra()));
            blueSell.setText("$" + format.format(response.getNewDolarValues().getNewBlueVenta()));
            card.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta() * 1.2));
            
            Date date = new Date(response.getTimestamp()*1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ' a las ' HH:mm:ss");
            datetime.setText(dateFormat.format(date));
            
        }
        
        if (!isOnline()){        	
        	Toast toast = Toast.makeText(getApplicationContext(), "No posee conexion a internet en este momento", Toast.LENGTH_LONG);
    		toast.show();
        } else if (source == null){        	
        	Toast toast = Toast.makeText(getApplicationContext(), "No pudimos traer los datos. Intente mas tarde", Toast.LENGTH_LONG);
    		toast.show();
        }
                
    }
    
    private InputStream retrieveStream(String url) {
    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpPost getRequest = new HttpPost(url);
        try{
        	HttpResponse getResponse = client.execute(getRequest);
        	final int statusCode = getResponse.getStatusLine().getStatusCode();
        	if(statusCode != HttpStatus.SC_OK) {
        		return null;
        	}
        	HttpEntity getResponseEntity = getResponse.getEntity();
        	return getResponseEntity.getContent();
        } catch (IOException e) {
        	getRequest.abort();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
