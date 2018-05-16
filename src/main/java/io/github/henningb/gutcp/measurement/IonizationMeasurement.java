package io.github.henningb.gutcp.measurement;

public class IonizationMeasurement {
	
	public int atomicNumber;
	
	public int ionCharge;
	
	public String elementName;
	
	public Double ionizationEnergy;
	
	public Double uncertainty;
	
	public String[] references;
	
	public String[] referencesUrl;
	
	public boolean theoretical;
	
	public boolean extrapolation;
	
	@Override
	public String toString() {
		return "" + atomicNumber + " " + elementName + " " + ionCharge + ": " + String.valueOf(ionizationEnergy).toLowerCase() + " eV (" + String.valueOf(uncertainty).toLowerCase() + ")" + (theoretical ? "t" : "") + (extrapolation ? "e" : "");
	}
	
}
