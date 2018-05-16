package io.github.henningb.gutcp.measurement;

public class ConstantMeasurement {
	
	public String quantity;
	
	public double value;
	
	public double uncertainty;
	
	public String unit;
	
	@Override
	public String toString() {
		return "" + quantity + ": " + String.valueOf(value).toLowerCase() + " (" + String.valueOf(uncertainty).toLowerCase() + ") " + unit + "";
	}
	
}
