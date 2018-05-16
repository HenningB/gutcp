package io.github.henningb.gutcp.calculation;

public class TwoElectronAtomCalculator extends Calculator {
	
	public TwoElectronAtomCalculator(AtomCalculator atomCalculator, Constants constants) {
		super(atomCalculator, constants);
	}
	
	public double radius(double Z, double A) {
		double radius = a_0 * (1 / (Z - 1) - root_3_4 / (Z * (Z - 1)));
		// System.out.println("Z=" + Z + " r=" + radius);
		return radius;
	}
	
	public double ionizationEnergy(double Z, double A) {
		double factor = 0.0000001d;
		double r = radius(Z, A);
		double E_ele = (Z - 1.0d) * e / (8.0d * pi * epsilon_0 * r);
		double E_mag = 2.0d * pi * 4.0d * factor * pi * e * sqr(h_bar) / (sqr(m_e) * pow(r, 3.0d));
		double E;
		if (Z == 2.0d) {
			double currentGamma = gamma(Z, A);
			E = E_ele * currentGamma + E_mag;
		} else {
			double previousGamma = gamma(Z - 1, A);
			E = E_ele * previousGamma - 1.0d / Z * E_mag;
		}
		System.out.println("Z=" + Z + " r=" + r + " E=" + E);
		return E;
	}
	
	private double gamma(double Z, double A) {
		double r = radius(Z, A);
		double v = h_bar / (m_e * r);
		double term1 = sqrt(1.0d - sqr(v / c));
		double term2 = 2.0d * pi * term1 * (sin(pi / 2.0d * pow(term1, 3.0d))) + (cos(pi / 2.0d * pow(term1, 3.0d)));
		double gamma_inv = term2 / (2.0d * pi);
		double gamma = 1.0d / gamma_inv;
		return gamma;
	}
	
}
