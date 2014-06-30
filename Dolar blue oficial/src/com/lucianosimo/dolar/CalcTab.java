package com.lucianosimo.dolar;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalcTab extends Fragment{

	private TextView labelIngresoValor;
	private TextView valueConversorOficial;
	private TextView labelConversorOficial;
	private TextView labelConversorBlue;
	private TextView valueConversorBlue;
	private FrameLayout lineConversorBlue;
	private TextView labelConversorCard;
	private TextView valueConversorCard;
	private FrameLayout lineConversorCard;
	
	private ImageView button_dap;
	private ImageView button_pad;
	private EditText editValor;
	
	private float valorOficial;
	private float valorBlue;
	private float newOficialSell;
	private float newBlueSell;

	private Context context = null;
	private Typeface tf;
	
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
		tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");

		newOficialSell = sharedPreferences.getFloat("newOficialSell", 0);
		newBlueSell = sharedPreferences.getFloat("newBlueSell", 0);
		
		editValor = (EditText) getView().findViewById(R.id.editValor);
		editValor.setText("");
		labelIngresoValor = (TextView) getView().findViewById(R.id.labelIngresoValor);
		valueConversorOficial = (TextView) getView().findViewById(R.id.valueConversorOficial);
		labelConversorOficial = (TextView) getView().findViewById(R.id.labelConversorOficial);
		button_dap = (ImageView) getView().findViewById(R.id.tab1);
		button_pad = (ImageView) getView().findViewById(R.id.tab2);
		labelConversorBlue = (TextView) getView().findViewById(R.id.labelConversorBlue);
		valueConversorBlue = (TextView) getView().findViewById(R.id.valueConversorBlue);
		lineConversorBlue = (FrameLayout) getView().findViewById(R.id.lineValue2);
		labelConversorCard = (TextView) getView().findViewById(R.id.labelConversorCard);
		valueConversorCard = (TextView) getView().findViewById(R.id.valueConversorCard);
		lineConversorCard = (FrameLayout) getView().findViewById(R.id.lineValue3);
		
		editValor.setTypeface(tf);
		labelIngresoValor.setTypeface(tf);
		labelConversorOficial.setTypeface(tf);
		valueConversorOficial.setTypeface(tf);
		labelConversorBlue.setTypeface(tf);
		valueConversorBlue.setTypeface(tf);
		labelConversorCard.setTypeface(tf);
		valueConversorCard.setTypeface(tf);
		
		final ImageView calcularButton = (ImageView) getView().findViewById(R.id.calcular_imagen);
		calcularButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editValor.getWindowToken(), 0);
				String stringValue = editValor.getText().toString();
				if (stringValue.equals("")) {
					valorOficial = 0;
					valueConversorOficial.setText("$ " + format.format(valorOficial));
				} else {					
					valorOficial = Float.parseFloat(stringValue) * newOficialSell;
					valueConversorOficial.setText("$ " + format.format(valorOficial));
				}
	
			}
		});
		
		button_dap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				labelConversorOficial.setText("Pesos");
				editValor.setText("");
				valueConversorOficial.setText("0.00");
				button_dap.setImageResource(R.drawable.button_dap_selected);
				button_pad.setImageResource(R.drawable.button_pad_unselected);
				labelConversorBlue.setVisibility(View.INVISIBLE);
				valueConversorBlue.setVisibility(View.INVISIBLE);
				lineConversorBlue.setVisibility(View.INVISIBLE);
				labelConversorCard.setVisibility(View.INVISIBLE);
				valueConversorCard.setVisibility(View.INVISIBLE);
				lineConversorCard.setVisibility(View.INVISIBLE);

				calcularButton.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(editValor.getWindowToken(), 0);
						String stringValue = editValor.getText().toString();
						if (stringValue.equals("")) {
							valorOficial = 0;
							valueConversorOficial.setText("$ " + format.format(valorOficial));
						} else {					
							valorOficial = Float.parseFloat(stringValue) * newOficialSell;
							valueConversorOficial.setText("$ " + format.format(valorOficial));
						}
			
					}
				});				
			}
		});
		
		button_pad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				labelConversorOficial.setText("Oficial");
				editValor.setText("");
				valueConversorOficial.setText("0.00");
				valueConversorBlue.setText("0.00");
				valueConversorCard.setText("0.00");
				button_dap.setImageResource(R.drawable.button_dap_unselected);
				button_pad.setImageResource(R.drawable.button_pad_selected);
				labelConversorBlue.setVisibility(View.VISIBLE);
				valueConversorBlue.setVisibility(View.VISIBLE);
				lineConversorBlue.setVisibility(View.VISIBLE);
				labelConversorCard.setVisibility(View.VISIBLE);
				valueConversorCard.setVisibility(View.VISIBLE);
				lineConversorCard.setVisibility(View.VISIBLE);

				calcularButton.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(editValor.getWindowToken(), 0);
						String stringValue = editValor.getText().toString();
						if (stringValue.equals("")) {
							valueConversorOficial.setText("$ " + format.format(0));
							valueConversorBlue.setText("$ " + format.format(0));
							valueConversorCard.setText("$ " + format.format(0));
						} else {					
							valorOficial = Float.parseFloat(stringValue) / newOficialSell;
							valorBlue = Float.parseFloat(stringValue) / newBlueSell;
							valueConversorOficial.setText("$ " + format.format(valorOficial));
							valueConversorBlue.setText("$ " + format.format(valorBlue));
							valueConversorCard.setText("$ " + format.format(valorOficial / 1.35));
						}
		
					}
				});				
			}
		});
		
		
		super.onViewCreated(view, savedInstanceState);
	}
}
