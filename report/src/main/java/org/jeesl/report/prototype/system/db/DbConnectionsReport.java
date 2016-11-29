package org.jeesl.report.prototype.system.db;

import org.jeesl.report.prototype.AbstractJeeslReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsDbFacade;
import net.sf.ahtutils.xml.report.Report;

public class DbConnectionsReport extends AbstractJeeslReport //implements JeeslReportHeader//,JeeslFlatReport,JeeslXlsReport
{
	final static Logger logger = LoggerFactory.getLogger(DbConnectionsReport.class);

	private UtilsDbFacade fDb;
	
	public DbConnectionsReport(String localeCode, UtilsDbFacade fDb)
	{
		super(localeCode);
		this.fDb=fDb;
		
		headers.add("Transaction");
		headers.add("Query");
		headers.add("Change");
		headers.add("Waiting");
		headers.add("State");
		headers.add("Query");

	}
	
	public Report build(String dbName)
	{
		flats = fDb.dbConnections(dbName);
		return new Report();
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