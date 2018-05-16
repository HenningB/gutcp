package io.github.henningb.gutcp.measurement;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.henningb.gutcp.measurement.IsotopeMeasurement.Isotope;

// https://catalog.data.gov/dataset/nist-codata-fundamental-physical-constants-srd-121
// https://nist.gov/srd/srd_data/srd121_allascii.json
public class IsotopesReader {
	
	public List<IsotopeMeasurement> readIsotopeMeasurements() throws IOException {
		List<IsotopeMeasurement> result = new ArrayList<>();
		Reader reader = ResourceHelper.getResourceAsReader(this.getClass(), "srd144_Atomic_Weights_and_Isotopic_Compositions_for_All_Elements.json", StandardCharsets.ISO_8859_1);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(reader);
		JsonNode arrayNode = rootNode.get("data");
		for (JsonNode atomicNode : arrayNode) {
			IsotopeMeasurement isotopeMeasurement = new IsotopeMeasurement();
			isotopeMeasurement.isotopes = new ArrayList<>();
			String atomicNumberString = atomicNode.get("Atomic Number").asText();
			String atomicSymbolString = atomicNode.get("Atomic Symbol").asText();
			isotopeMeasurement.atomicNumber = Integer.parseInt(atomicNumberString);
			isotopeMeasurement.atomicSymbol = atomicSymbolString;
			JsonNode isotopesNode = atomicNode.get("isotopes");
			for (JsonNode isotopeNode : isotopesNode) {
				Isotope isotope = new Isotope();
				String massNumberString = isotopeNode.get("Mass Number").asText();
				isotope.massNumber = Integer.parseInt(massNumberString);
				JsonNode relativeAtomicMassNode = isotopeNode.get("Relative Atomic Mass");
				if (relativeAtomicMassNode != null)
				{
					String relativeAtomicMassString = relativeAtomicMassNode.asText();
					Number[] valueAndUncertainty = parseValueAndUncertainty(relativeAtomicMassString);
					isotope.relativeAtomicMass = (Double) valueAndUncertainty[0];
					isotope.massUncertainty = (Integer) valueAndUncertainty[1];
				}
				JsonNode isotopicCompositionNode = isotopeNode.get("Isotopic Composition");
				if (isotopicCompositionNode != null)
				{
					String isotopicCompositionString = isotopicCompositionNode.asText();
					Number[] valueAndUncertainty = parseValueAndUncertainty(isotopicCompositionString);
					isotope.isotopicComposition = (Double) valueAndUncertainty[0];
					isotope.compositionUncertainty = (Integer) valueAndUncertainty[1];
				}
				isotopeMeasurement.isotopes.add(isotope);
			}
			println(isotopeMeasurement);
			result.add(isotopeMeasurement);
		}
		return result;
	}
	
	private Number[] parseValueAndUncertainty(String string) {
		Matcher matcher = Pattern.compile("[\\d\\.]+").matcher(string);
		matcher.find();
		String valueString = matcher.group();
		Double value = Double.parseDouble(valueString);
		Integer uncertainty = null;
		if (matcher.find())
		{
			String uncertaintyString = matcher.group();
			uncertainty = Integer.parseInt(uncertaintyString);
		}
		return new Number[] { value, uncertainty };
	}
	
	private static void println(Object object) {
		// System.out.println(String.valueOf(object));
	}
	
}
