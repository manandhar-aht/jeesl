package org.jeesl.factory.docx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.Tbl;
import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.factory.docx.Docx4jDocumentFactory;
import org.jeesl.factory.docx.DocxStyleBean;
import org.jeesl.factory.docx.XwpfDocumentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractJeeslTest;

public class TestDocx4jAndXwpfDocumentFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDocx4jAndXwpfDocumentFactory.class);
	final static String text = "We test POI!!! :-)";
	
	public static void main(String[] args) throws Exception
	{
		AbstractJeeslTest.initTargetDirectory();
		JeeslUtilTestBootstrap.init();
		
		Docx4jDocumentFactory testDocx4j = new Docx4jDocumentFactory();
		
		boolean landscape = false;
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, landscape);
		ObjectFactory factory = Context.getWmlObjectFactory();
		testDocx4j.setWordMLPackage(wordMLPackage);
		testDocx4j.setFactory(factory);
		testDocx4j.setPageMargins();
		DocxStyleBean defStyle = new DocxStyleBean();
		defStyle.setFontColor("FF0080");
		
		Tbl table = testDocx4j.createTableWithContent();
		wordMLPackage.getMainDocumentPart().addObject(table);
		wordMLPackage.save(new File("src\\test\\resources\\data\\docx\\testDocx4J.docx"));
		
		 TestDocx4jAndXwpfDocumentFactory test = new TestDocx4jAndXwpfDocumentFactory();
		 test.testParagraph();
		 test.testAddBorders();
		 test.testWorkingWithTemplates();
		
	}
	
	@SuppressWarnings("unused")
	private void testAddBorders() throws IOException
	{
		XwpfDocumentFactory xDF = new XwpfDocumentFactory();
		xDF.createDocument();
		
		xDF.createParagraph(text, ParagraphAlignment.LEFT, "Arial", 6, false, false);
		
		xDF.createParagraph(text, ParagraphAlignment.RIGHT, "Consolas", 8, false, true);
		xDF.applyingBorder(Borders.DOUBLE);
		xDF.createParagraph(text, ParagraphAlignment.CENTER, "Calibri", 10, true, false);
		xDF.applyingBorder(Borders.DOUBLE_D);
		xDF.writeDocument("src\\test\\resources\\data\\docx\\testBorders.docx");
	}
	
	@SuppressWarnings("unused")
	private void testParagraph() throws IOException
	{
		XwpfDocumentFactory xDF = new XwpfDocumentFactory();
		xDF.createDocument();
		xDF.createParagraph(text, ParagraphAlignment.LEFT, "Arial", 6, false, false);
		xDF.createParagraph(text, ParagraphAlignment.RIGHT, "Consolas", 8, false, true);
		xDF.createParagraph(text, ParagraphAlignment.CENTER, "Calibri", 10, true, false);
		xDF.writeDocument("src\\test\\resources\\data\\docx\\testParagraph.docx");
	}
	
	@SuppressWarnings("unused")
	private void testWorkingWithTemplates() throws FileNotFoundException, IOException
	{
		
		String template = "src\\test\\resources\\data\\docx\\templates\\template001.docx";
		
		XwpfDocumentFactory xDF = new XwpfDocumentFactory();
		xDF.setFileStream2Document(new FileInputStream(template));
		
		xDF.selectTable(0);
		xDF.selectTableField(0, 0);
		xDF.insertDefAndTextIntoTableField("hallo", "00-00", 0);
		xDF.selectTableField(0, 1);
		xDF.insertDefAndTextIntoTableField("hallo", "00-11", 0);
		xDF.selectTableField(1, 0);
		xDF.insertDefAndTextIntoTableField("hallo", "11-00", 0);
		xDF.selectTableField(1, 1);
		xDF.insertDefAndTextIntoTableField("hallo", "11-11", 0);
		
		xDF.writeDocument("src\\test\\resources\\data\\docx\\template001.docx");
		
	}
	
}
