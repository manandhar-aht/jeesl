package org.jeesl.factory.xls.system.io.report;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XlsSheetFactory
{
	final static Logger logger = LoggerFactory.getLogger(XlsSheetFactory.class);
		
	public static Sheet getSheet(Workbook wb, String sheetName)
	{
		Sheet sheet;
		if (wb.getSheet(sheetName) == null){sheet = wb.createSheet(sheetName);}
		else {sheet = wb.getSheet(sheetName);}
		return sheet;
	}
}