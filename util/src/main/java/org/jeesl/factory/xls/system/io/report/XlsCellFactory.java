package org.jeesl.factory.xls.system.io.report;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XlsCellFactory
{
	final static Logger logger = LoggerFactory.getLogger(XlsCellFactory.class);
			
	public static void build(Row xlsRow, MutableInt columnNr, CellStyle style, String value)
	{
		Cell cell = xlsRow.createCell(columnNr.intValue());
        cell.setCellStyle(style);
        cell.setCellValue(value);
        columnNr.add(1);
	}
}