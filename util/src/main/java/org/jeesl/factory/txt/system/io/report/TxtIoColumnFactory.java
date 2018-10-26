package org.jeesl.factory.txt.system.io.report;

import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtIoColumnFactory<COLUMN extends JeeslReportColumn<?,?,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoColumnFactory.class);
		
	@SuppressWarnings("unused")
	private String localeCode;
	
	public TxtIoColumnFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public String position(COLUMN column)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(column.getGroup().getSheet().getPosition());
		sb.append(".").append(column.getGroup().getPosition());
		sb.append(".").append(column.getPosition());
		return sb.toString();
	}
}