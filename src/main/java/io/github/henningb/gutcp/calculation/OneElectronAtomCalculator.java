package io.github.henningb.gutcp.calculation;

public class OneElectronAtomCalculator extends Calculator {
	
	public OneElectronAtomCalculator(AtomCalculator atomCalculator, Constants constants) {
		super(atomCalculator, constants);
	}
	
	public double radius(double Z, double A) {
		double radius = a_0 / Z * (sqrt(1 - sqr(alpha * Z / (1 + m_e / (2 * m_P * A)))) + m_e / (m_P * A));
		return radius;
	}
	
	public double ionizationEnergy(double Z, double A) {
		double r = radius(Z, A);
		double T = m_e * sqr(c) * (1 / sqrt(1 - sqr(alpha * Z / (1 + m_e / (2 * m_P * A)))) - 1) / e;
		double V = Z * e / (4 * pi * epsilon_0 * r);
		double E = V - T;
		// System.out.println("r=" + r + " T=" + T + " V=" + V + " E" + E);
		return E;
	}
	
}
