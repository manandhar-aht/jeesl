package org.jeesl.factory.xml.system.io.report;

import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
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
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.report.Report;

public class XmlReportFactory <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XmlReportFactory.class);
	
	private Report q;
	
	private XmlCategoryFactory<CATEGORY,L,D> xfCategory;
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;

	public XmlReportFactory(Query q){this(q.getLang(), q.getReport());}
	public XmlReportFactory(String localeCode, Report q)
	{
		this.q=q;
		
		if(q.isSetCategory()){xfCategory = new XmlCategoryFactory<CATEGORY,L,D>(q.getCategory());}
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
	}
	
	public Report build(REPORT report)
	{
		Report xml = new Report();
		
//		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()){xml.setCode(report.getCode());}
		if(q.isSetVisible()){xml.setVisible(report.isVisible());}
		if(q.isSetPosition()){xml.setPosition(report.getPosition());}
		
		if(q.isSetCategory()){xml.setCategory(xfCategory.build(report.getCategory()));}	
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(report.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(report.getDescription()));}
		
		
		
		return xml;
	}
}