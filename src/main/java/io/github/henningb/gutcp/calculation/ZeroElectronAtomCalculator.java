package io.github.henningb.gutcp.calculation;

public class ZeroElectronAtomCalculator extends Calculator {
	
	public ZeroElectronAtomCalculator(AtomCalculator atomCalculator, Constants constants) {
		super(atomCalculator, constants);
	}
	
	public double radius(double Z, double A) {
		return 0;
	}
	
	public double ionizationEnergy(double Z, double A) {
		return 0;
	}
	
}
