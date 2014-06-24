package com.lucianosimo.dolar.model;

import com.google.gson.annotations.SerializedName;

public class OldDolarValues {
	
	@SerializedName("old_oficial_compra")
	private float old_oficial_compra;
	
	@SerializedName("old_oficial_venta")
	private float old_oficial_venta;
	
	@SerializedName("old_blue_compra")
	private float old_blue_compra;
	
	@SerializedName("old_blue_venta")
	private float old_blue_venta;
	
	public float getOldOficialCompra() {
		return old_oficial_compra;
	}
	
	public float getOldOficialVenta() {
		return old_oficial_venta;
	}
	
	public float getOldBlueCompra() {
		return old_blue_compra;
	}
	
	public float getOldBlueVenta() {
		return old_blue_venta;
	}

}
