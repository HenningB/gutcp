package io.github.henningb.gutcp.calculation;

public class Constants {
	
	// physical constats
	public double a_0;
	public double alpha;
	public double c;
	public double e;
	public double epsilon_0;
	public double g;
	public double h_bar;
	public double m_e;
	public double m_P;
	public double mag_mom_P;
	public double nu_0;
	
	public String toString() {
		String result = "\n";
		result += "a\u2080 = " + String.valueOf(a_0).toLowerCase() + "\n";
		result += "\u03B1 = " + String.valueOf(alpha).toLowerCase() + "\n";
		result += "c = " + String.valueOf(c).toLowerCase() + "\n";
		result += "e = " + String.valueOf(e).toLowerCase() + "\n";
		result += "\u03B5\u2080 = " + String.valueOf(epsilon_0).toLowerCase() + "\n";
		result += "g = " + String.valueOf(g).toLowerCase() + "\n";
		result += "\u210F = " + String.valueOf(h_bar).toLowerCase() + "\n";
		result += "m\u2091 = " + String.valueOf(m_e).toLowerCase() + "\n";
		result += "m\u209A = " + String.valueOf(m_P).toLowerCase() + "\n";
		result += "\u03BC\u209A = " + String.valueOf(mag_mom_P).toLowerCase() + "\n";
		result += "\u03BD\u2080 = " + String.valueOf(nu_0).toLowerCase() + "\n";
		return result;
	}
	
}
