package org.jeesl.factory.xml.system.io.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportQueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.report.Queries;
import net.sf.ahtutils.xml.report.Row;
import net.sf.ahtutils.xml.report.Template;

public class XmlTemplateFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,
								CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplateFactory.class);
	
	private Template q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
//	private XmlLayoutFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE> xfLayout;
	
	public XmlTemplateFactory(Template q){this(null,q);}
	public XmlTemplateFactory(String localeCode, Template q)
	{
		this.q=q;
//		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
//		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
//		if(q.isSetLayout()){xfLayout = new XmlLayoutFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>(localeCode,q.getLayout());}
	}
	
	public Template build(TEMPLATE template)
	{
		Template xml = XmlTemplateFactory.build();
		
/*		if(q.isSetCode()){xml.setCode(row.getCode());}
		if(q.isSetVisible()){xml.setVisible(row.isVisible());}
		if(q.isSetPosition()){xml.setPosition(row.getPosition());}
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(row.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(row.getDescription()));}
		if(q.isSetType()){xml.setType(xfType.build(row.getType()));}
		if(q.isSetDataType() && row.getDataType()!=null){xml.setDataType(xfDataType.build(row.getDataType()));}
		
		if(q.isSetQueries()){xml.setQueries(queries(row));}
		if(q.isSetLayout()){xml.setLayout(xfLayout.build(row));}
*/						
		return xml;
	}
	
	public static Template build()
	{
		Template xml = new Template();
		return xml;
	}
}