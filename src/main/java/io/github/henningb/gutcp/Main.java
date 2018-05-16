package io.github.henningb.gutcp;

import java.io.File;
import java.io.IOException;

import freemarker.template.TemplateException;
import io.github.henningb.gutcp.render.ChartData;
import io.github.henningb.gutcp.render.ChartRenderer;

public class Main {
	
	public static void main(String[] args) throws IOException, TemplateException {
		ChartDataBuilder chartDataBuilder = new ChartDataBuilder();
		chartDataBuilder.initialize();
		chartDataBuilder.printMeasurements();
		ChartData[][] chartDataArray = chartDataBuilder.createChartData();
		ChartRenderer chartRenderer = new ChartRenderer();
		File file = new File("ionization-chart.html");
		chartRenderer.render(file, chartDataArray);
	}
	
}
