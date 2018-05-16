package io.github.henningb.gutcp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.github.henningb.gutcp.calculation.AtomCalculator;
import io.github.henningb.gutcp.calculation.Constants;
import io.github.henningb.gutcp.measurement.ConstantMeasurement;
import io.github.henningb.gutcp.measurement.ConstantsReader;
import io.github.henningb.gutcp.measurement.IonizationMeasurement;
import io.github.henningb.gutcp.measurement.IonizationReader;
import io.github.henningb.gutcp.measurement.IsotopeMeasurement;
import io.github.henningb.gutcp.measurement.IsotopesReader;
import io.github.henningb.gutcp.measurement.IsotopeMeasurement.Isotope;
import io.github.henningb.gutcp.render.ChartData;

public class ChartDataBuilder {
	
	Constants constants;
	List<IsotopeMeasurement> isotopeMeasurements;
	List<IonizationMeasurement> ionizationMeasurements;
	AtomCalculator atomCalculator;
	
	public ChartDataBuilder() {
	}
	
	public void initialize() throws IOException {
		readMeasurements();
		atomCalculator = new AtomCalculator(constants);
	}
	
	private void readMeasurements() throws IOException {
		ConstantsReader constantsReader = new ConstantsReader();
		Map<String,ConstantMeasurement> constantModelMap = constantsReader.readConstantModel();
		constants = buildConstants(constantModelMap);
		IsotopesReader isotopesReader = new IsotopesReader();
		isotopeMeasurements = isotopesReader.readIsotopeMeasurements();
		IonizationReader ionizationReader = new IonizationReader();
		ionizationMeasurements = ionizationReader.readIonizationMeasurements();
	}
	
	private static Constants buildConstants(Map<String,ConstantMeasurement> constantModelMap) {
		Constants constants = new Constants();
		constants.a_0 = constantModelMap.get("atomic unit of length").value;
		constants.alpha = constantModelMap.get("fine-structure constant").value;
		constants.c = constantModelMap.get("speed of light in vacuum").value;
		constants.e = constantModelMap.get("atomic unit of charge").value;
		constants.epsilon_0 = constantModelMap.get("electric constant").value;
		constants.g = constantModelMap.get("electron g factor").value;
		constants.h_bar = constantModelMap.get("Planck constant over 2 pi").value;
		constants.m_e = constantModelMap.get("electron mass").value;
		constants.m_P = constantModelMap.get("proton mass").value;
		constants.mag_mom_P = constantModelMap.get("proton mag. mom.").value;
		constants.nu_0 = constantModelMap.get("mag. constant").value;
		return constants;
	}
	
	public void printMeasurements() {
		System.out.println(constants);
		for (IsotopeMeasurement isotopeMeasurement : isotopeMeasurements) {
			System.out.println(isotopeMeasurement);
		}
		for (IonizationMeasurement ionizationMeasurement : ionizationMeasurements) {
			System.out.println(ionizationMeasurement);
		}
	}
	
	public ChartData[][] createChartData() {
		ChartData[][] chartDataArray = new ChartData[isotopeMeasurements.size() + 1][isotopeMeasurements.size() + 1];
		for (IsotopeMeasurement isotopeMeasurement : isotopeMeasurements) {
			int Z = isotopeMeasurement.atomicNumber;
			for (int electrons = 1; electrons <= Z; electrons++) {
				ChartData chartData = new ChartData();
				chartDataArray[Z][Z - electrons] = chartData;
				IonizationMeasurement ionizationMeasurement = findIonizationMeasurement(Z, electrons);
				chartData.atomicSymbol = isotopeMeasurement.atomicSymbol;
				chartData.atomicNumber = isotopeMeasurement.atomicNumber;
				if (ionizationMeasurement != null) {
					chartData.ionCharge = ionizationMeasurement.ionCharge + 1;
					chartData.ionizationMeasurement = ionizationMeasurement.ionizationEnergy;
					chartData.references = ionizationMeasurement.references;
					chartData.referencesUrl = ionizationMeasurement.referencesUrl;
					chartData.theoretical = ionizationMeasurement.theoretical;
					chartData.extrapolation = ionizationMeasurement.extrapolation;
				}
				chartData.ionizationCalculation = calculateIonizationEnergy(isotopeMeasurement, Z, electrons);
				chartData.backgroundColor = "#ffffff";
				if (chartData.ionizationMeasurement != null && chartData.ionizationCalculation != null) {
					chartData.error = (chartData.ionizationMeasurement - chartData.ionizationCalculation) / chartData.ionizationMeasurement;
					double error = chartData.error;
					String color = colorizeError(error);
					chartData.backgroundColor = color;
					System.out.println("err=" + chartData.error + " color=" + color);
				}
			}
		}
		return chartDataArray;
	}

	private String colorizeError(double error) {
		double colorFactor = (Math.log10(Math.abs(error)) + 2.0d) / 2.0d;
		colorFactor = (colorFactor > 0.0d ? colorFactor : 0.0d);
		colorFactor = (colorFactor < 1.0d ? colorFactor : 1.0d);
		long colorValue = Math.round(colorFactor * 255);
		String red = Long.toString(colorValue, 16);
		red = red.length() == 2 ? red : "0" + red;
		String green = Long.toString(255 - colorValue, 16);
		green = green.length() == 2 ? green : "0" + green;
		String color = "#" + red + green + "00";
		return color;
	}
	
	private Double calculateIonizationEnergy(IsotopeMeasurement isotopeMeasurement, int Z, int electrons) {
		if (!atomCalculator.canCalculate(electrons)) {
			return null;
		}
		double addedIonizationEnergy = 0;
		double averagedIonizationEnergy = 0;
		List<Isotope> isotopes = isotopeMeasurement.isotopes;
		for (Isotope isotope : isotopes) {
			int A = isotope.massNumber;
			@SuppressWarnings("unused")
			double radius = atomCalculator.radius(Z, A, electrons);
			double ionizationEnergy = atomCalculator.ionizationEnergy(Z, A, electrons);
			// System.out.println("Z=" + Z + " A=" + A + " r=" + radius + " E=" + ionizationEnergy);
			addedIonizationEnergy += ionizationEnergy;
			if (isotope.isotopicComposition != null) {
				averagedIonizationEnergy += ionizationEnergy * isotope.isotopicComposition;
			}
		}
		if (averagedIonizationEnergy != 0) {
			return averagedIonizationEnergy;
		} else {
			return addedIonizationEnergy / isotopes.size();
		}
	}
	
	private IonizationMeasurement findIonizationMeasurement(int Z, int electrons) {
		for (IonizationMeasurement ionizationMeasurement : ionizationMeasurements) {
			if (ionizationMeasurement.atomicNumber == Z && ionizationMeasurement.ionCharge == Z - electrons) {
				return ionizationMeasurement;
			}
		}
		return null;
	}
	
}
