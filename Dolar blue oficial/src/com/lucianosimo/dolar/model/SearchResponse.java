package com.lucianosimo.dolar.model;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {

	//El nombre de la variable tiene que ser si o si el nombre del objeto json
	private NewDolarValues new_dolar_values;
	private OldDolarValues old_dolar_values;
	
	@SerializedName("timestamp_changed")
	private long timestamp_changed;
	
	@SerializedName("averages_oficial")
	private String averages_oficial;
	
	@SerializedName("averages_blue")
	private String averages_blue;
	
	public long getTimestamp() {
		return timestamp_changed;
	}
	
	public NewDolarValues getNewDolarValues() {
		return new_dolar_values;
	}
	
	public OldDolarValues getOldDolarValues() {
		return old_dolar_values;
	}
	
	public String getAverageOficial() {
		return averages_oficial;
	}
	
	public String getAverageBlue() {
		return averages_blue;
	}
}
