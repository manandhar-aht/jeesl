package org.jeesl.controller.report.module;

import java.util.Date;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.report.AbstractJeeslReport;
import org.jeesl.factory.xml.module.ts.XmlDataFactory;
import org.jeesl.factory.xml.module.ts.XmlTimeSeriesFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Report;

public class TimeSeriesReport <L extends UtilsLang,D extends UtilsDescription,
						CATEGORY extends UtilsStatus<CATEGORY,L,D>,
						REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
						WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						STYLE extends JeeslReportStyle<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
						CDT extends UtilsStatus<CDT,L,D>,
						CW extends UtilsStatus<CW,L,D>,
						RT extends UtilsStatus<RT,L,D>,
						ENTITY extends EjbWithId,
						ATTRIBUTE extends EjbWithId,
						FILLING extends UtilsStatus<FILLING,L,D>,
						TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
						
						CAT extends UtilsStatus<CAT,L,D>,
						SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
						UNIT extends UtilsStatus<UNIT,L,D>,
						TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
						BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
						EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
						INT extends UtilsStatus<INT,L,D>,
						DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
						WS extends UtilsStatus<WS,L,D>,
						QAF extends UtilsStatus<QAF,L,D>
						>
					extends AbstractJeeslReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>
//implements JeeslReportHeader//,JeeslFlatReport,JeeslXlsReport
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesReport.class);

	private final Class<TS> cTs;
	private final JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> fTs;
	
	public TimeSeriesReport(String localeCode,
			final JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport,
			final JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> fTs,
			final Class<L> cL,final Class<D> cD,
			final Class<CATEGORY> cCategory,final Class<REPORT> cReport,
			final Class<IMPLEMENTATION> cImplementation, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet,
			final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow,
			final Class<TEMPLATE> cTemplate, final Class<CELL> cCell, final Class<STYLE> cStyle,
			final Class<CDT> cDataType,
			final Class<CW> cColumnWidth, Class<RT> cRowType, final Class<TRANSFORMATION> cTransformation,
			final Class<TS> cTs)
	{
		super(localeCode,cL,cD,cCategory,cReport);
		super.initIo(fReport,this.getClass(),cImplementation,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cStyle,cDataType,cColumnWidth,cRowType,cTransformation);
		this.fTs=fTs;
		this.cTs=cTs;
	}
	
	public Report build(WS workspace, SCOPE scope, INT interval, BRIDGE bridge, Date from, Date to) throws UtilsNotFoundException
	{
		TS ts = fTs.fTimeSeries(cTs, scope, interval, bridge);
		List<DATA> tsData = fTs.fData(workspace,ts,from,to);
		logger.info("Records: "+tsData.size());
		Report xml = XmlReportFactory.build();
		
		TimeSeries xTs = XmlTimeSeriesFactory.build();
		
		for(DATA data : tsData)
		{
			xTs.getData().add(XmlDataFactory.build(data.getRecord(),data.getValue()));
		}
		
		xml.getTimeSeries().add(xTs);
		
		return xml;
	}
	
}
/*
	public Report build()
	{
		r = new Report();
		rTitle = "Project Data Entry Report";
		rFooter = rTitle;
				
		r.setInfo(getInfo());
		XmlLabelsFactory.aggregationGroups(localeCode,r.getInfo().getLabels(),aggregations);
		XmlLabelsFactory.aggregationHeader(localeCode,r.getInfo().getLabels(),mapAggregationLabels);
		r.setMeis(new Meis());
		
		return r;
	
	@Override public void buildFlat()
	{
		flats = JsonFlatFiguresFactory.build();
		for(MeisProject project : fProject.all(MeisProject.class))
		{
			JsonFlatFigure json = new JsonFlatFigure();
			
			project = fProject.load(project);
			MeisProgram program = null;
			if(!project.getPrograms().isEmpty())
			{
				program = project.getPrograms().get(0);
				program = fProgram.load(program);
			}
			
			if(program!=null)
			{
				json.setG1(program.getCode());
				json.setG2(program.getName());
			}
			json.setG3(project.getCode());
			json.setG4(project.getName());
			json.setG5(project.getDistrict().getName());
			if(project.getCoverage()!=null){json.setG6(TxtSectorFactory.build(project.getCoverage().getSectors()));}
			
			json.setG7(project.getTypeMain().getName().get(localeCode).getLang());
			json.setG8(project.getType().getName().get(localeCode).getLang());
			if(project.getTypeSecond()!=null){json.setG9(project.getTypeSecond().getName().get(localeCode).getLang());}
			
			if(program!=null && !program.getBudgets().isEmpty())
			{
				json.setG10(TxtContributionFactory.fundingSourceCode(program.getBudgets()));
			}
			
			flats.getFigures().add(json);
		}
	}
*/