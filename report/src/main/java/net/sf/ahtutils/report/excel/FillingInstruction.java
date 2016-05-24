package net.sf.ahtutils.report.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author helgehemmer
 */
public interface FillingInstruction {
	
	public XSSFWorkbook       fill(XSSFWorkbook workbook);	
}
