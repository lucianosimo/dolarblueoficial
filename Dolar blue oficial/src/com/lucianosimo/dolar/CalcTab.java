package com.lucianosimo.dolar;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalcTab extends Fragment{

	private TextView valueConversorOficial;
	private TextView labelConversorOficial;
	private EditText editValor;
	
	private float valorOficial;
	private float newOficialSell;
	
	private Context context = null;
	
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
        return (RelativeLayout)inflater.inflate(R.layout.calc_tab, container, false);
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		final DecimalFormat format = new DecimalFormat("0.00");
		newOficialSell = sharedPreferences.getFloat("newOficialSell", 0);
		
		valueConversorOficial = (TextView) getView().findViewById(R.id.valueConversorOficial);
		labelConversorOficial = (TextView) getView().findViewById(R.id.labelConversorOficial);
		labelConversorOficial.setText("Pesos");
		editValor = (EditText) getView().findViewById(R.id.editValor);		
		
		ImageView calcularButton = (ImageView) getView().findViewById(R.id.calcular_imagen);
		calcularButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editValor.getWindowToken(), 0);
				valorOficial = Float.parseFloat(editValor.getText().toString()) * newOficialSell;
				valueConversorOficial.setText("$ " + format.format(valorOficial));				
			}
		});
		
		super.onViewCreated(view, savedInstanceState);
	}
}
