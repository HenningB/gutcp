package io.github.henningb.gutcp.measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

public class ResourceHelper {
	
	public static Reader getResourceAsReader(Class<?> parallelClass, String resourceName, Charset charset) {
		String packagePath = parallelClass.getPackage().getName().replaceAll("\\.", "/");
		InputStream inputStream = parallelClass.getClassLoader().getResourceAsStream(packagePath + "/" + resourceName);
		Reader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
		return reader;
	}
	
	public static InputStream getResourceAsInputStream(Class<?> parallelClass, String resourceName) {
		String packagePath = parallelClass.getPackage().getName().replaceAll("\\.", "/");
		InputStream inputStream = parallelClass.getClassLoader().getResourceAsStream(packagePath + "/" + resourceName);
		return inputStream;
	}
	
	public static void printReader(Reader reader) throws IOException {
		int ch;
		while ((ch = reader.read()) != -1) {
			System.out.print((char) ch);
		}
	}
	
	public static void printFieldNames(JsonNode node) {
		Iterator<String> fieldNames = node.fieldNames();
		while (fieldNames.hasNext()) {
			System.out.println(fieldNames.next());
		}
	}
	
}
