package org.jeesl.factory.mc.ts;

import java.util.List;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.metachart.factory.xml.chart.XmlDataFactory;
import org.metachart.factory.xml.chart.XmlDataSetFactory;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McTsViewerFactory <TS extends JeeslTimeSeries<?,?,?>, DATA extends JeeslTsData<TS,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(McTsViewerFactory.class);

	public McTsViewerFactory()
	{

	}
	
	public DataSet build(List<DATA> list)
	{
		DataSet dsValue = XmlDataSetFactory.build();
		for(DATA d : list)
		{
			if(d.getValue() != null && d.getRecord() != null)
			{
				dsValue.getData().add(XmlDataFactory.build(d.getValue(), d.getRecord()));
			}

		}
		return XmlDataSetFactory.build(dsValue);
	}
}