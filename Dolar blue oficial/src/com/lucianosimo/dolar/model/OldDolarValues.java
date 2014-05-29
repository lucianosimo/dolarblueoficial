package com.lucianosimo.dolar.model;

import com.google.gson.annotations.SerializedName;

public class OldDolarValues {
	
	@SerializedName("old_oficial_compra")
	private double old_oficial_compra;
	
	@SerializedName("old_oficial_venta")
	private double old_oficial_venta;
	
	@SerializedName("old_blue_compra")
	private double old_blue_compra;
	
	@SerializedName("old_blue_venta")
	private double old_blue_venta;
	
	public double getOldOficialCompra() {
		return old_oficial_compra;
	}
	
	public double getOldOficialVenta() {
		return old_oficial_venta;
	}
	
	public double getOldBlueCompra() {
		return old_blue_compra;
	}
	
	public double getOldBlueVenta() {
		return old_blue_venta;
	}

}
