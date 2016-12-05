package org.jeesl.factory.xml.system.io.report;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;

public class XmlXlsSheetFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlXlsSheetFactory.class);
	
	private XlsSheet q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;

//	private XmlSheetsFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION,IMPLEMENTATION> xfSheets;

	public XmlXlsSheetFactory(String localeCode, XlsSheet q)
	{
		this.q=q;
//		if(q.isSetXlsSheets()){xfSheets = new XmlSheetsFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION,IMPLEMENTATION>(localeCode,q.getXlsSheets());}
		if(getLangs(q)!=null){xfLangs = new XmlLangsFactory<L>(getLangs(q));}
		if(getDescriptions(q)!=null){xfDescriptions = new XmlDescriptionsFactory<D>(getDescriptions(q));}
	}
	
	public XlsSheet build(SHEET sheet)
	{
		XlsSheet xml = new XlsSheet();
		
		if(getLangs(q)!=null){xml.getContent().add(xfLangs.getUtilsLangs(sheet.getName()));}
		if(getDescriptions(q)!=null){xml.getContent().add(xfDescriptions.create(sheet.getDescription()));}
						
		return xml;
	}
	
	private Langs getLangs(XlsSheet q)
	{
		for(Serializable s : q.getContent())
		{
			if(s instanceof Langs){return (Langs)s;}
		}
		return null;
	}
	
	private Descriptions getDescriptions(XlsSheet q)
	{
		for(Serializable s : q.getContent())
		{
			if(s instanceof Descriptions){return (Descriptions)s;}
		}
		return null;
	}
}