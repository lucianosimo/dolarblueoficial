package com.lucianosimo.dolar;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucianosimo.dolar.helper.FuncHelper;
import com.lucianosimo.dolar.model.SearchResponse;

public class MainActivity extends Activity {

	private static final String URL = "http://loomalabs.com/servicedolar/dolar/getDolarInfo.json";
	private final Context context = this;
	private ProgressDialog progressDialog = null;	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        if(FuncHelper.isOnline(context)) {
        	
        	this.progressDialog = ProgressDialog.show(this,"Por favor espere...","Estamos actualizando la informacion",true,true);
			new DownloadTask().execute("Start Download");
			
			ImageView refreshButton = (ImageView) findViewById(R.id.refresh_image);
			refreshButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Por favor espere...", "Estamos actualizando la informacion", true);
					
					new AsyncTask<Void, Void, Void>() {
						
				        @Override
				        protected Void doInBackground(Void... params) {
				            retrieveInfo();
				            return null;
				        }

				        @Override
				        protected void onPostExecute(Void result) {
				            dialog.dismiss();
				        }
				    }.execute();
				}
			});
            
        } else {        	
        	Toast toast = Toast.makeText(getApplicationContext(), "No posee conexion a internet en este momento", Toast.LENGTH_LONG);
    		toast.show();
        }
                
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class DownloadTask extends AsyncTask<String, Void, Object> {

		@Override
		protected Object doInBackground(String... params) {
			retrieveInfo();
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			
			if (MainActivity.this.progressDialog != null) {
	            MainActivity.this.progressDialog.dismiss();
			}
		}
		
	}
    
    
    @SuppressLint("SimpleDateFormat")
	public void retrieveInfo() {
    	
    	final TextView blueBuy = (TextView) findViewById(R.id.valueBuyBlue);
        final TextView blueSell = (TextView) findViewById(R.id.valueSellBlue);
        final TextView oficialBuy = (TextView) findViewById(R.id.valueBuyOficial);
        final TextView oficialSell = (TextView) findViewById(R.id.valueSellOficial);
        final TextView datetime = (TextView) findViewById(R.id.valueDatetime);
        final TextView card = (TextView) findViewById(R.id.valueCard);
        
        final ImageView arrowOficialBuy = (ImageView) findViewById(R.id.arrowBuyOficial);
        final ImageView arrowOficialSell = (ImageView) findViewById(R.id.arrowSellOficial);
        final ImageView arrowBlueBuy = (ImageView) findViewById(R.id.arrowBuyBlue);
        final ImageView arrowBlueSell = (ImageView) findViewById(R.id.arrowSellBlue);
        final ImageView arrowCard = (ImageView) findViewById(R.id.arrowCard);
    	
    	Gson gson = new Gson();
    	InputStream source = FuncHelper.retrieveStream(URL);
    	Reader reader = new InputStreamReader(source);
    	final SearchResponse response = gson.fromJson(reader, SearchResponse.class);
    	
    	final double oldOficialbuy = response.getOldDolarValues().getOldOficialCompra();
    	final double oldOficialSell = response.getOldDolarValues().getOldOficialVenta();
    	final double oldBlueBuy = response.getOldDolarValues().getOldBlueCompra();
    	final double oldBlueSell = response.getOldDolarValues().getOldBlueVenta();
    	
    	final double newOficialbuy = response.getNewDolarValues().getNewOficialCompra();
    	final double newOficialSell = response.getNewDolarValues().getNewOficialVenta();
    	final double newBlueBuy = response.getNewDolarValues().getNewBlueCompra();
    	final double newBlueSell = response.getNewDolarValues().getNewBlueVenta();
        
    	runOnUiThread(new Runnable() {
			
			public void run() {
				DecimalFormat format = new DecimalFormat("0.00");
				oficialBuy.setText("$" + format.format(response.getNewDolarValues().getNewOficialCompra()));
		        oficialSell.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta()));
		        blueBuy.setText("$" + format.format(response.getNewDolarValues().getNewBlueCompra()));
		        blueSell.setText("$" + format.format(response.getNewDolarValues().getNewBlueVenta()));
		        card.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta() * 1.2));
		        
		        if (newOficialbuy < oldOficialbuy) {
		        	arrowOficialBuy.setImageResource(R.drawable.down);
		        	arrowCard.setImageResource(R.drawable.down);
		        } else if (newOficialbuy > oldOficialbuy) {
		        	arrowOficialBuy.setImageResource(R.drawable.up);
		        	arrowCard.setImageResource(R.drawable.up);
		        } else {
		        	arrowOficialBuy.setImageResource(R.drawable.equal);
		        	arrowCard.setImageResource(R.drawable.equal);
		        }
		        
		        if (newOficialSell < oldOficialSell) {
		        	arrowOficialSell.setImageResource(R.drawable.down);
		        } else if (newOficialSell > oldOficialSell) {
		        	arrowOficialSell.setImageResource(R.drawable.up);
		        } else {
		        	arrowOficialSell.setImageResource(R.drawable.equal);
		        }
		        
		        if (newBlueBuy < oldBlueBuy) {
		        	arrowBlueBuy.setImageResource(R.drawable.down);
		        } else if (newBlueBuy > oldBlueBuy) {
		        	arrowBlueBuy.setImageResource(R.drawable.up);
		        } else {
		        	arrowBlueBuy.setImageResource(R.drawable.equal);
		        }
		        
		        if (newBlueSell < oldBlueSell) {
		        	arrowBlueSell.setImageResource(R.drawable.down);
		        } else if (newBlueSell > oldBlueSell) {
		        	arrowBlueSell.setImageResource(R.drawable.up);
		        } else {
		        	arrowBlueSell.setImageResource(R.drawable.equal);
		        }
		        
		        Date date = new Date(response.getTimestamp()*1000);
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ' a las ' HH:mm:ss");
		        datetime.setText(dateFormat.format(date));
			}
		});
    	
        
    	
    }

}
