package com.lucianosimo.dolar;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucianosimo.dolar.helper.FuncHelper;
import com.lucianosimo.dolar.model.SearchResponse;

public class ValueTab extends Fragment{
	
	private static final String URL = "http://loomalabs.com/servicedolar/dolar/getDolarInfo.json";
	private static final float cardValueRate = 1.35f;
	private static final float ahorroValueRate = 1.2f;
	
	private Context context = null;
	private ProgressDialog progressDialog = null;
	private Typeface tf;
	private Typeface tfBold;
	
	private TextView buy;
	private TextView sell;
	private TextView oficialText;
	private TextView blueText;
	private TextView cardText;
	private TextView ahorroText;
	private TextView datetimeText;
	
	private TextView blueBuy;
    private TextView blueSell;
    private TextView oficialBuy;
    private TextView oficialSell;
    private TextView datetime;
    private TextView card;
    private TextView ahorro;
    
    private ImageView arrowOficialBuy;
    private ImageView arrowOficialSell;
    private ImageView arrowBlueBuy;
    private ImageView arrowBlueSell;
    private ImageView arrowCard;
    private ImageView arrowAhorro;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	context = getActivity();
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        return (RelativeLayout)inflater.inflate(R.layout.value_tab, container, false);
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        tfBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
		
        oficialText = (TextView) getView().findViewById(R.id.oficial);
        blueText = (TextView) getView().findViewById(R.id.blue);
        cardText = (TextView) getView().findViewById(R.id.card);
        ahorroText = (TextView) getView().findViewById(R.id.ahorro);
        datetimeText = (TextView) getView().findViewById(R.id.datetime);
        buy = (TextView) getView().findViewById(R.id.buy);
        sell = (TextView) getView().findViewById(R.id.sell);
        blueBuy = (TextView) getView().findViewById(R.id.valueBuyBlue);
        blueSell = (TextView) getView().findViewById(R.id.valueSellBlue);
        oficialBuy = (TextView) getView().findViewById(R.id.valueBuyOficial);
        oficialSell = (TextView) getView().findViewById(R.id.valueSellOficial);
        datetime = (TextView) getView().findViewById(R.id.valueDatetime);
        card = (TextView) getView().findViewById(R.id.valueCard);
        ahorro = (TextView) getView().findViewById(R.id.valueAhorro);
        
        arrowOficialBuy = (ImageView) getView().findViewById(R.id.arrowBuyOficial);
        arrowOficialSell = (ImageView) getView().findViewById(R.id.arrowSellOficial);
        arrowBlueBuy = (ImageView) getView().findViewById(R.id.arrowBuyBlue);
        arrowBlueSell = (ImageView) getView().findViewById(R.id.arrowSellBlue);
        arrowCard = (ImageView) getView().findViewById(R.id.arrowCard);
        arrowAhorro = (ImageView) getView().findViewById(R.id.arrowAhorro);
        
        buy.setTypeface(tfBold);
        sell.setTypeface(tfBold);
        datetimeText.setTypeface(tfBold);
        
        oficialText.setTypeface(tfBold);
        blueText.setTypeface(tfBold);
        cardText.setTypeface(tfBold);
        ahorroText.setTypeface(tfBold);

        blueBuy.setTypeface(tf);
        blueSell.setTypeface(tf);
        oficialBuy.setTypeface(tf);
        oficialSell.setTypeface(tf);
        datetime.setTypeface(tf);
        card.setTypeface(tf);
        ahorro.setTypeface(tf);
        
        if(FuncHelper.isOnline(context)) {
        
	        this.progressDialog = ProgressDialog.show(getActivity(),"Por favor espere...","Estamos actualizando la informacion", true, false);
	        new DownloadTask().execute("Start Download");
	        
	        ImageView refreshButton = (ImageView) getView().findViewById(R.id.refresh_image);
			refreshButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Por favor espere...", "Estamos actualizando la informacion", true);
					
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
			Toast toast = Toast.makeText(context, "No posee conexion a internet en este momento", Toast.LENGTH_LONG);
    		toast.show();
		}
        super.onViewCreated(view, savedInstanceState);
	}
	
	private class DownloadTask extends AsyncTask<String, Void, Object> {

		@Override
		protected Object doInBackground(String... params) {
			retrieveInfo();
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			
			if (ValueTab.this.progressDialog != null) {
	            ValueTab.this.progressDialog.dismiss();
			}
		}
		
	}
    
    
    @SuppressLint("SimpleDateFormat")
	public void retrieveInfo() {
    	
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
        
    	getActivity().runOnUiThread(new Runnable() {
			
			public void run() {
				DecimalFormat format = new DecimalFormat("0.00");
				oficialBuy.setText("$" + format.format(response.getNewDolarValues().getNewOficialCompra()));
		        oficialSell.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta()));
		        blueBuy.setText("$" + format.format(response.getNewDolarValues().getNewBlueCompra()));
		        blueSell.setText("$" + format.format(response.getNewDolarValues().getNewBlueVenta()));
		        card.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta() * cardValueRate));
		        ahorro.setText("$" + format.format(response.getNewDolarValues().getNewOficialVenta() * ahorroValueRate));
		        
		        if (newOficialbuy < oldOficialbuy) {
		        	arrowOficialBuy.setImageResource(R.drawable.down);
		        	arrowCard.setImageResource(R.drawable.down);
		        	arrowAhorro.setImageResource(R.drawable.down);
		        } else if (newOficialbuy > oldOficialbuy) {
		        	arrowOficialBuy.setImageResource(R.drawable.up);
		        	arrowCard.setImageResource(R.drawable.up);
		        	arrowAhorro.setImageResource(R.drawable.up);
		        } else {
		        	arrowOficialBuy.setImageResource(R.drawable.equal);
		        	arrowCard.setImageResource(R.drawable.equal);
		        	arrowAhorro.setImageResource(R.drawable.equal);
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
