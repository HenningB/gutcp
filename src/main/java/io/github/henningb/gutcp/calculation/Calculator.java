package io.github.henningb.gutcp.calculation;

public abstract class Calculator {
	
	protected final AtomCalculator atomCalculator;
	
	// physical constats
	protected final double a_0;
	protected final double alpha;
	protected final double c;
	protected final double e;
	protected final double epsilon_0;
	protected final double g;
	protected final double h_bar;
	protected final double m_e;
	protected final double m_P;
	protected final double mag_mom_P;
	protected final double nu_0;
	// calculated constants
	protected final double a_H;
	// mathematical constants
	protected final double pi;
	protected final double root_3_4;
	
	protected Calculator(AtomCalculator atomCalculator, Constants constants) {
		this.atomCalculator = atomCalculator;
		// physical constats
		a_0 = constants.a_0;
		alpha = constants.alpha;
		c = constants.c;
		e = constants.e;
		epsilon_0 = constants.epsilon_0;
		g = constants.g;
		h_bar = constants.h_bar;
		m_e = constants.m_e;
		m_P = constants.m_P;
		mag_mom_P = constants.mag_mom_P;
		nu_0 = constants.nu_0;
		// calculated constants
		a_H = constants.a_0 * ((constants.m_P + constants.m_e) / constants.m_P);
		// mathematical constants
		pi = Math.PI;
		root_3_4 = sqrt(3.0d / 4.0d);
	}
	
	protected double sqr(double input) {
		return input * input;
	}
	
	protected double pow(double input1, double input2) {
		return Math.pow(input1, input2);
	}
	
	protected double sqrt(double input) {
		return Math.sqrt(input);
	}
	
	protected double sin(double input) {
		return Math.sin(input);
	}
	
	protected double cos(double input) {
		return Math.cos(input);
	}
	
	abstract public double radius(double Z, double A);
	
	abstract public double ionizationEnergy(double Z, double A);
	
}
