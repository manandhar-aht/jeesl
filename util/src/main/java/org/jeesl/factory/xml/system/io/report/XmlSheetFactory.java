package org.jeesl.factory.xml.system.io.report;

import java.util.Collections;
import java.util.Comparator;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportQueryType;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Queries;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class XmlSheetFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSheetFactory.class);
	
	private XlsSheet q;
	
	private Comparator<GROUP> cGroup;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xfGroup;
	private XmlRowsFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xfRows;

	public XmlSheetFactory(String localeCode, XlsSheet q)
	{
		this.q=q;
		cGroup = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportGroupComparator.Type.position);
	
		try {xfLangs = new XmlLangsFactory<L>(ReportXpath.getLangs(q));} catch (ExlpXpathNotFoundException e) {}
		try {xfDescriptions = new XmlDescriptionsFactory<D>(ReportXpath.getDescriptions(q));} catch (ExlpXpathNotFoundException e) {}
		try {xfGroup = new XmlColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(localeCode,ReportXpath.getColumnGroup(q));}catch (ExlpXpathNotFoundException e) {}
		try {xfRows = new XmlRowsFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(localeCode,ReportXpath.getRows(q));}catch (ExlpXpathNotFoundException e) {}
	}
	
	public XlsSheet build(SHEET sheet)
	{
		XlsSheet xml = new XlsSheet();
		
		if(q.isSetCode()){xml.setCode(sheet.getCode());}
		if(q.isSetVisible()){xml.setVisible(sheet.isVisible());}
		if(q.isSetPosition()){xml.setPosition(sheet.getPosition());}
		
		try {ReportXpath.getLangs(q);xml.getContent().add(xfLangs.getUtilsLangs(sheet.getName()));} catch (ExlpXpathNotFoundException e) {}
		try {ReportXpath.getDescriptions(q);xml.getContent().add(xfDescriptions.create(sheet.getDescription()));} catch (ExlpXpathNotFoundException e) {}
		
		try {ReportXpath.getQueries(q);xml.getContent().add(queries(sheet));}catch (ExlpXpathNotFoundException e) {}
		
		try
		{
			ReportXpath.getColumnGroup(q);
			Collections.sort(sheet.getGroups(),cGroup);
			for(GROUP g : sheet.getGroups())
			{
				xml.getContent().add(xfGroup.build(g));
			}
		}
		catch (ExlpXpathNotFoundException e) {}
		
		try
		{
			ReportXpath.getRows(q);
			xml.getContent().add(xfRows.build(sheet));
		}
		catch (ExlpXpathNotFoundException e) {}
		
		return xml;
	}
	
	private Queries queries(SHEET sheet)
	{
		Queries xml = XmlQueriesFactory.build();
		if(sheet.getQueryTable()!=null){xml.getQuery().add(XmlQueryFactory.build(JeeslReportQueryType.Sheet.table, sheet.getQueryTable()));}
		return xml;
	}
}