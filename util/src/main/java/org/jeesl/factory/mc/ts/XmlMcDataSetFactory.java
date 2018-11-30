package org.jeesl.factory.mc.ts;

import java.util.List;

import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;

public class XmlMcDataSetFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMcDataSetFactory.class);

	public static DataSet build(TimeSeries timeSeries)
	{
		DataSet ds = new DataSet();
		
		for(org.jeesl.model.xml.module.ts.Data tsD: timeSeries.getData())
		{
			if (tsD.isSetValue())
			{
				Data cd = new Data();
				cd.setRecord(tsD.getRecord());

				cd.setY(tsD.getValue());
				ds.getData().add(cd);
			}
		}
		return ds;	
	}
	
	public static <DATA extends JeeslTsData<?,?,?,?>> DataSet build(List<DATA> datas)
	{
		DataSet ds = new DataSet();
		
		for(DATA data: datas)
		{
			Data cd = new Data();
			cd.setRecord(DateUtil.toXmlGc(data.getRecord()));
			if(data.getValue()!=null) {cd.setY(data.getValue());}
			ds.getData().add(cd);
		}
		return ds;	
	}
}