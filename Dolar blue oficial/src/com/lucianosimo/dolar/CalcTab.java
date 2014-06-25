package com.lucianosimo.dolar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalcTab extends Fragment{

	private TextView valueConversorOficial;
	private EditText editValor;
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
		float newOficialSell = sharedPreferences.getFloat("newOficialSell", 0);
		/*valueConversorOficial = (TextView) getView().findViewById(R.id.valueConversorOficial);
		editValor = (EditText) getView().findViewById(R.id.editValor);
		float valorOficial = Float.parseFloat(editValor.getText().toString()) * newOficialSell;
		valueConversorOficial.setText("" + valorOficial);
		super.onViewCreated(view, savedInstanceState);*/
	}
}
