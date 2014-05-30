package com.lucianosimo.dolar;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucianosimo.dolar.helper.FuncHelper;
import com.lucianosimo.dolar.model.SearchResponse;

public class UpdateService extends Service {

	private static final String URL = "http://loomalabs.com/servicedolar/dolar/getDolarInfo.json";
	private final Context context = this;
	
	@Override
	public void onStart(Intent intent, int startId) {
		if(FuncHelper.isOnline(context)) {
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
		DecimalFormat format = new DecimalFormat("0.00");
    	Gson gson = new Gson();
        InputStream source = FuncHelper.retrieveStream(URL);
        
        if (source != null) {
        	Reader reader = new InputStreamReader(source);
        	SearchResponse response = gson.fromJson(reader, SearchResponse.class);
            
            updateViews.setTextViewText(R.id.valueBuyOficial, "$" + format.format(response.getNewDolarValues().getNewOficialCompra()));
            updateViews.setTextViewText(R.id.valueSellOficial, "$" + format.format(response.getNewDolarValues().getNewOficialVenta()));
            updateViews.setTextViewText(R.id.valueBuyBlue, "$" + format.format(response.getNewDolarValues().getNewBlueCompra()));
            updateViews.setTextViewText(R.id.valueSellBlue, "$" + format.format(response.getNewDolarValues().getNewBlueVenta()));
            
            return updateViews;
        } else {
        	Toast toast = Toast.makeText(getApplicationContext(), "No pudimos traer los datos. Intente mas tarde", Toast.LENGTH_LONG);
    		toast.show();
    		return null;
        }
    	
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
