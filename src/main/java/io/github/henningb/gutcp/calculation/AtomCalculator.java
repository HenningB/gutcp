package io.github.henningb.gutcp.calculation;

import java.util.ArrayList;
import java.util.List;

//Exact Classical Quantum Mechanical Solutions for One- Through Twenty-Electron Atoms – R.L. Mills, Physics Essays, Vol. 18, No. 3, September, (2005), pp. 321–361.
//http://www.physicsessays.org/browse-journal-2/product/887-6-randell-l-mills-exact-classical-quantum-mechanical-solutions-for-one-through-twenty-electron-atoms.html

public class AtomCalculator {
	
	List<Calculator> calculators;
	
	public AtomCalculator(Constants constants) {
		calculators = new ArrayList<>();
		calculators.add(new ZeroElectronAtomCalculator(this,constants));
		calculators.add(new OneElectronAtomCalculator(this, constants));
		calculators.add(new TwoElectronAtomCalculator(this, constants));
	}
	
	public boolean canCalculate(int electrons) {
		boolean calculateable = electrons > 0 && electrons < calculators.size();
		return calculateable;
	}
	
	public double radius(int Z, int A, int electrons) {
		Calculator calculator = calculators.get(electrons);
		double radius = calculator.radius(Z, A);
		return radius;
	}
	
	public double ionizationEnergy(int Z, int A, int electrons) {
		Calculator calculator = calculators.get(electrons);
		double ionizationEnergy = calculator.ionizationEnergy(Z, A);
		return ionizationEnergy;
	}
	
}
