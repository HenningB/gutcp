package io.github.henningb.gutcp.measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// https://physics.nist.gov/cgi-bin/ASD/ie.pl?at_num_out=on&biblio=on&e_out=0&el_name_out=on&encodedlist=XXT2&format=0&ion_charge_out=on&ion_conf_out=on&level_out=on&order=0&seq_out=on&shells_out=on&sp_name_out=on&spectra=H-U&unc_out=on&units=1
public class IonizationReader {
	
	private static final String BASE_URI = "https://physics.nist.gov/cgi-bin/ASD/ie.pl?at_num_out=on&biblio=on&e_out=0&el_name_out=on&encodedlist=XXT2&format=0&ion_charge_out=on&ion_conf_out=on&level_out=on&order=0&seq_out=on&shells_out=on&sp_name_out=on&spectra=H-U&unc_out=on&units=1";
	
	public List<IonizationMeasurement> readIonizationMeasurements() throws IOException {
		List<IonizationMeasurement> result = new ArrayList<>();
		InputStream inputStream = ResourceHelper.getResourceAsInputStream(this.getClass(), "NIST Atomic Ionization Energies Output.htm");
		Document document = Jsoup.parse(inputStream, "ISO_8859_1", BASE_URI);
		Element table = document.select("table[frame='box']").first();
		Elements headings = table.select("tr th");
		Map<String,Integer> columnMap = new HashMap<>();
		int index = 0;
		for (Element heading : headings) {
			String headingName = heading.ownText();
			columnMap.put(headingName, index);
			index++;
			println("'" + headingName + "'");
		}
		Elements rows = table.select("tr.bsl");
		for (Element row : rows) {
			IonizationMeasurement ionizationModel = new IonizationMeasurement();
			Elements cells = row.select("td");
			String atomNumberString = cells.get(columnMap.get("At. Num.").intValue()).ownText();
			String ionChargeString = cells.get(columnMap.get("Ion Charge").intValue()).ownText();
			String elementNameString = cells.get(columnMap.get("El. name").intValue()).ownText();
			String ionizationEnergyString = cells.get(columnMap.get("Ionization Energy (eV)").intValue()).ownText();
			String uncertaintyString = cells.get(columnMap.get("Uncertainty (eV)").intValue()).ownText();
			String theoreticalString = cells.get(columnMap.get("Ionization Energy (eV)").intValue()).text();
			String extrapolationString = cells.get(columnMap.get("Ionization Energy (eV)").intValue()).text();
			println(atomNumberString);
			println(ionChargeString);
			println(elementNameString);
			println(ionizationEnergyString);
			println(uncertaintyString);
			println(theoreticalString.startsWith("("));
			println(extrapolationString.startsWith("["));
			ionizationModel.atomicNumber = Integer.parseInt(atomNumberString);
			ionizationModel.ionCharge = Integer.parseInt(ionChargeString);
			ionizationModel.elementName = elementNameString;
			ionizationModel.ionizationEnergy = parseDouble(ionizationEnergyString);
			ionizationModel.uncertainty = parseDouble(uncertaintyString);
			ionizationModel.theoretical = theoreticalString.startsWith("(");
			ionizationModel.extrapolation = extrapolationString.startsWith("[");
			Element referencesElement = cells.get(columnMap.get("References").intValue());
			Elements referenceElements = referencesElement.select("a");
			for (Element referenceElement : referenceElements) {
				String referenceString = referenceElement.ownText();
				String referenceOnclick = referenceElement.attr("onclick");
				String referenceHref = null;
				String[] splitted = referenceOnclick.split("\\'");
				if (splitted.length >= 3) {
					referenceHref = splitted[1];
				}
				println(referenceString);
				println(referenceHref);
			}
			println("");
			result.add(ionizationModel);
		}
		return result;
	}
	
	private Double parseDouble(String ionizationEnergyString) {
		try {
			return Double.parseDouble(ionizationEnergyString.replaceAll("\\s", ""));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private static void println(Object object) {
		// System.out.println(String.valueOf(object));
	}
	
}
