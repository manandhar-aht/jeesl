package net.sf.ahtutils.report.revert.excel.importers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import net.sf.ahtutils.interfaces.controller.report.UtilsXlsDefinitionResolver;

import net.sf.ahtutils.report.revert.excel.AbstractExcelImporter;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelSimpleSerializableImporter <S extends Serializable, I extends ImportStrategy>
	extends AbstractExcelImporter<S,I> {
	
	final static Logger logger = LoggerFactory.getLogger(ExcelSimpleSerializableImporter.class);
	
	public ExcelSimpleSerializableImporter(UtilsXlsDefinitionResolver resolver, String code, String filename) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		// Initialize the Abstract class with the given filename
		super(resolver, code, filename);
	}

	public ExcelSimpleSerializableImporter(UtilsXlsDefinitionResolver resolver, String code, InputStream is) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		// Initialize the Abstract class with the given inputstream
		super(resolver, code, is);
	}

	public ExcelSimpleSerializableImporter(XlsSheet definition, String filename) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		// Initialize the Abstract class with the given filename
		super(definition, filename);
	}
	
	public ExcelSimpleSerializableImporter(XlsSheet definition, InputStream is) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		// Initialize the Abstract class with the given inputstream
		super(definition, is);
	}		
	/**
	* Because the ExcelStatusImporter is highly parameterized with classes to implement certain aspects, this method
	* can be used to get an instance that is configured correctly according to your use case
	*
	* @param cClass           Concrete class to be imported (must implements Serializable)
	* @param cLang            Concrete class that implements UtilsLang
	* @param cDescription     Concrete class that implements UtilsDescription
	* @param cStatus          Concrete class that implements UtilsStatus
	* @param defaultLanguages Array of language codes (en, fr, de, ...)
	* @param filename         Location of the Excel-Sheet
	*
	* @return New instance of ExcelStatusImporter
	*
	*/
	public static <S extends Serializable, C extends Serializable, I extends ImportStrategy>
	ExcelSimpleSerializableImporter<S,I> factory(UtilsXlsDefinitionResolver resolver, String code, String filename) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		return new ExcelSimpleSerializableImporter<S,I>(resolver, code, filename);
	}
	
	public static <S extends Serializable, C extends Serializable, I extends ImportStrategy>
	ExcelSimpleSerializableImporter<S,I> factory(UtilsXlsDefinitionResolver resolver, String code, InputStream is) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		return new ExcelSimpleSerializableImporter<S,I>(resolver, code, is);
	}
	
	public static <S extends Serializable, C extends Serializable, I extends ImportStrategy>
	ExcelSimpleSerializableImporter<S,I> factory(XlsSheet definition, String filename) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		return new ExcelSimpleSerializableImporter<S,I>(definition, filename);
	}
	
	public static <S extends Serializable, C extends Serializable, I extends ImportStrategy>
	ExcelSimpleSerializableImporter<S,I> factory(XlsSheet definition, InputStream is) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		return new ExcelSimpleSerializableImporter<S,I>(definition, is);
	}
}
