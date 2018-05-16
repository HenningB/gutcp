package io.github.henningb.gutcp.measurement;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// https://catalog.data.gov/dataset/nist-codata-fundamental-physical-constants-srd-121
// https://nist.gov/srd/srd_data/srd121_allascii.json
public class ConstantsReader {
	
	public Map<String,ConstantMeasurement> readConstantModel() throws IOException {
		Map<String,ConstantMeasurement> result = new HashMap<>();
		Reader reader = ResourceHelper.getResourceAsReader(this.getClass(), "srd121_allascii_2014.json", StandardCharsets.ISO_8859_1);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(reader);
		JsonNode arrayNode = rootNode.get("constant");
		for (JsonNode constantNode : arrayNode) {
			ConstantMeasurement constantMeasurement = new ConstantMeasurement();
			constantMeasurement.quantity = constantNode.get("Quantity ").asText();
			String valueString = constantNode.get("Value").asText();
			constantMeasurement.value = Double.parseDouble(valueString.replaceAll("\\s", "").replaceAll("\\.\\.\\.", ""));
			String uncertaintyString = constantNode.get("Uncertainty").asText();
			constantMeasurement.uncertainty = Double.parseDouble(uncertaintyString.replaceAll("\\s", "").replaceAll("\\(exact\\)", "0"));
			constantMeasurement.unit = constantNode.get("Unit").asText();
			println(constantMeasurement);
			result.put(constantMeasurement.quantity, constantMeasurement);
		}
		return result;
	}
	
	private static void println(Object object) {
		// System.out.println(String.valueOf(object));
	}
	
}
