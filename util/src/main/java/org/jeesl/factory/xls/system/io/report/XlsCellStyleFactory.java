package org.jeesl.factory.xls.system.io.report;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XlsCellStyleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XlsCellStyleFactory.class);
			
	public static CellStyle label(Workbook workbook, Font font)
	{
		CellStyle style = workbook.createCellStyle();
//		style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(font);
		return style;
	}
}