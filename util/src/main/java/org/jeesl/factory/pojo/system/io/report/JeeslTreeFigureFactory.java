package org.jeesl.factory.pojo.system.io.report;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.controller.report.JeeslPivotFactory;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.finance.XmlFiguresFactory;
import net.sf.ahtutils.interfaces.controller.report.JeeslPivotAggregator;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.finance.Figures;

public class JeeslTreeFigureFactory
{
	private final static Logger logger = LoggerFactory.getLogger(JeeslTreeFigureFactory.class);
	
	public static <L extends UtilsLang, D extends UtilsDescription, A extends UtilsStatus<A,L,D>,TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
		Figures build(String localeCode, JeeslPivotFactory<L,D,A> pivotFactory, int lvl, List<A> aggregations, JeeslPivotAggregator dpa, List<EjbWithId> parents, TRANSFORMATION transformation)
	{	
		JeeslReportSetting.Transformation myTransformation = JeeslReportSetting.Transformation.valueOf(transformation.getCode());
		
		Figures data = XmlFiguresFactory.build("data");
		if(aggregations!=null && !aggregations.isEmpty())
		{
			data.getFigures().addAll(JeeslTreeFigureFactory.aggregationLevel(localeCode,pivotFactory,0,aggregations,dpa,new ArrayList<EjbWithId>(),myTransformation));
		}
		
		Figures figures = XmlFiguresFactory.build();
		figures.getFigures().add(data);
		
		if(myTransformation.equals(JeeslReportSetting.Transformation.last))
		{
			logger.info("Transformation "+myTransformation);
			Figures last = XmlFiguresFactory.build("transformation");
			figures.getFigures().add(last);
		}
		
		return figures;
	}
	
	
	private static <L extends UtilsLang, D extends UtilsDescription, A extends UtilsStatus<A,L,D>, TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
		List<Figures> aggregationLevel(String localeCode, JeeslPivotFactory<L,D,A> pivotFactory, int lvl, List<A> aggregations, JeeslPivotAggregator dpa, List<EjbWithId> parents, JeeslReportSetting.Transformation transformation)
	{
		List<Figures> list = new ArrayList<Figures>();
		A aggregation = aggregations.get(lvl);
		for(EjbWithId ejb : dpa.list(pivotFactory.getIndexFor(aggregation)))
		{			
			Figures figures = XmlFiguresFactory.build(ejb.getId(), null, pivotFactory.buildTreeLevelName(localeCode, ejb));
			
			List<EjbWithId> path = new ArrayList<EjbWithId>();
			path.addAll(parents);
			path.add(ejb);
			
			if(lvl<aggregations.size()-1)
			{
				figures.getFinance().addAll(pivotFactory.buildFinance(dpa,path));
				figures.getFigures().addAll(aggregationLevel(localeCode,pivotFactory,lvl+1,aggregations,dpa,path,transformation));
			}
			else if(lvl==aggregations.size()-1)
			{
				figures.getFinance().addAll(pivotFactory.buildFinance(dpa,path));
			}
			
			if(XmlFiguresFactory.hasFinanceElements(figures)){list.add(figures);}
		}
		return list;
	}
}