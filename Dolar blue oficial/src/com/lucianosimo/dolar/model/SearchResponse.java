package com.lucianosimo.dolar.model;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {

	//El nombre de la variable tiene que ser si o si el nombre del objeto json
	private FieldOficialCompra field_oficial_compra;
	private FieldOficialVenta field_oficial_venta;
	private FieldBlueCompra field_blue_compra;
	private FieldBlueVenta field_blue_venta;
	
	@SerializedName("revision_timestamp")
	private long revision_timestamp;
	
	public long getTimestamp() {
		return revision_timestamp;
	}
	
	public FieldOficialCompra getFieldOficialCompra() {
		return field_oficial_compra;
	}
	
	public FieldOficialVenta getFieldOficialVenta() {
		return field_oficial_venta;
	}

	public FieldBlueCompra getFieldBlueCompra() {
		return field_blue_compra;
	}
	
	public FieldBlueVenta getFieldBlueVenta() {
		return field_blue_venta;
	}
}
