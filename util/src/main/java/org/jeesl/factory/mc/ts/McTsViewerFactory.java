package org.jeesl.factory.mc.ts;

import java.util.List;

import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.metachart.factory.xml.chart.XmlDataFactory;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McTsViewerFactory <TS extends JeeslTimeSeries<?,?,?,?,?>, DATA extends JeeslTsData<?,?,TS,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(McTsViewerFactory.class);

	public McTsViewerFactory()
	{

	}
	
	public DataSet build(List<DATA> list)
	{
		DataSet ds = new DataSet();
	
		DataSet dsValue = new DataSet();
		
		
		
		for(DATA d : list)
		{
			dsValue.getData().add(XmlDataFactory.build(d.getValue(),d.getRecord()));
		}
		
		ds.getDataSet().add(dsValue);

		return ds;
	}
}