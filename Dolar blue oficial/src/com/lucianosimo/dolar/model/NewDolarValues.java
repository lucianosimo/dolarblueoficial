package com.lucianosimo.dolar.model;

import com.google.gson.annotations.SerializedName;

public class NewDolarValues {
	
	@SerializedName("new_oficial_compra")
	private float new_oficial_compra;
	
	@SerializedName("new_oficial_venta")
	private float new_oficial_venta;
	
	@SerializedName("new_blue_compra")
	private float new_blue_compra;
	
	@SerializedName("new_blue_venta")
	private float new_blue_venta;
	
	public float getNewOficialCompra() {
		return new_oficial_compra;
	}
	
	public float getNewOficialVenta() {
		return new_oficial_venta;
	}
	
	public float getNewBlueCompra() {
		return new_blue_compra;
	}
	
	public float getNewBlueVenta() {
		return new_blue_venta;
	}

}
