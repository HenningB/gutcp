package io.github.henningb.gutcp.render;

public class ChartData {
	
	public String atomicSymbol;
	public int atomicNumber;
	public int ionCharge;
	public Double ionizationMeasurement;
	public String[] references;
	public String[] referencesUrl;
	public boolean theoretical;
	public boolean extrapolation;
	public Double ionizationCalculation;
	public Double error;
	public String backgroundColor;
	
	public String getAtomicSymbol() {
		return atomicSymbol;
	}
	
	public int getAtomicNumber() {
		return atomicNumber;
	}
	
	public int getIonCharge() {
		return ionCharge;
	}
	
	public Double getIonizationMeasurement() {
		return ionizationMeasurement;
	}
	
	public String[] getReferences() {
		return references;
	}
	
	public String[] getReferencesUrl() {
		return referencesUrl;
	}
	
	public boolean isTheoretical() {
		return theoretical;
	}
	
	public boolean isExtrapolation() {
		return extrapolation;
	}
	
	public Double getIonizationCalculation() {
		return ionizationCalculation;
	}
	
	public Double getError() {
		return error;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
}
