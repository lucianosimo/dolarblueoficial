package com.lucianosimo.dolar.model;

import com.google.gson.annotations.SerializedName;

public class NewDolarValues {
	
	@SerializedName("new_oficial_compra")
	private double new_oficial_compra;
	
	@SerializedName("new_oficial_venta")
	private double new_oficial_venta;
	
	@SerializedName("new_blue_compra")
	private double new_blue_compra;
	
	@SerializedName("new_blue_venta")
	private double new_blue_venta;
	
	public double getNewOficialCompra() {
		return new_oficial_compra;
	}
	
	public double getNewOficialVenta() {
		return new_oficial_venta;
	}
	
	public double getNewBlueCompra() {
		return new_blue_compra;
	}
	
	public double getNewBlueVenta() {
		return new_blue_venta;
	}

}
