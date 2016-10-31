package org.jeesl.report.excel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jeesl.model.json.JsonFlatFigure;
import org.jeesl.model.json.JsonFlatFigures;
import org.junit.Before;
import org.junit.Test;

public class JeeslExcelFlatFiguresExporterTest {
	
	private JsonFlatFigures flatFigures;
	private List<String>	headers;
	
	public JeeslExcelFlatFiguresExporterTest() {
	}
	
	@Before
	public void setUp() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		flatFigures = new JsonFlatFigures();
		List<JsonFlatFigure> figures = new ArrayList<JsonFlatFigure>();
		JsonFlatFigure figure1 = new JsonFlatFigure();
		figure1.setG1("Test G1");
		figure1.setG3("Test G3");
		JsonFlatFigure figure2 = new JsonFlatFigure();
		figure2.setG2("Test G2");
		figure2.setG4("Test G4");
		figures.add(figure1);
		figures.add(figure2);
		flatFigures.setFigures(figures);
		
		headers = new ArrayList<String>();
		headers.add("1: Only in 1");
		headers.add("2: Only in 2");
		headers.add("3: Only in 1");
		headers.add("4: Only in 2");
	}
	
	@Test
	public void export() throws IOException {
		JeeslExcelFlatFiguresExporter exporter = new JeeslExcelFlatFiguresExporter();
		byte[] excelData = exporter.export(headers, flatFigures);
		FileUtils.writeByteArrayToFile(new File("target/ExcelFlatFigureExporterTest.xlsx"), excelData);
	}
	
}
