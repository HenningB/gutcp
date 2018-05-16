package io.github.henningb.gutcp.measurement;

import java.util.List;

public class IsotopeMeasurement {
	
	public int atomicNumber;
	
	public String atomicSymbol;
	
	public List<Isotope> isotopes;
	
	@Override
	public String toString() {
		return "" + atomicNumber + " " + atomicSymbol + ": " + isotopes;
	}
	
	public static class Isotope {
		public int massNumber;
		public Double isotopicComposition;
		public Integer compositionUncertainty;
		public Double relativeAtomicMass;
		public Integer massUncertainty;
		@Override
		public String toString() {
			String string = "" + massNumber + ":";
			if (relativeAtomicMass != null) {
				string += " mass=" + String.valueOf(relativeAtomicMass).toLowerCase() + "(" + String.valueOf(massUncertainty).toLowerCase() + ")";
			}
			if (isotopicComposition != null) {
				string += " composition=" + String.valueOf(isotopicComposition).toLowerCase() + "(" + String.valueOf(compositionUncertainty).toLowerCase() + ")";
			}
			return string;
		}
	}
	
}
