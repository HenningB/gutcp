package io.github.henningb.gutcp.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ChartRenderer {
	
	private final Configuration configuration;
	
	public ChartRenderer() {
		this.configuration = configure();
	}
	
	private Configuration configure() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), ".");
		configuration.setTemplateLoader(templateLoader);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		configuration.setLogTemplateExceptions(false);
		return configuration;
	}
	
	public void render(File file, ChartData[][] chartDatas) throws TemplateException, IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
		Template template = configuration.getTemplate("ionization-chart.ftlh");
		Root root = new Root();
		root.chartDatas = chartDatas;
		template.process(root, writer);
	}
	
	public static class Root {
		public ChartData[][] chartDatas;
		public ChartData[][] getChartDatas() {
			return chartDatas;
		}
	}
	
}
