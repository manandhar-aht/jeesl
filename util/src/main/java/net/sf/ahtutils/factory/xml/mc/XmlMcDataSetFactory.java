package net.sf.ahtutils.factory.xml.mc;

import org.metachart.xml.Data;
import org.metachart.xml.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.ts.TimeSeries;

public class XmlMcDataSetFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMcDataSetFactory.class);

	
	public static DataSet build(TimeSeries timeSeries)
	{
		DataSet ds = new DataSet();
		
		for(net.sf.ahtutils.xml.ts.Data tsD: timeSeries.getData())
		{
			Data cd = new Data();
			cd.setRecord(tsD.getRecord());
			cd.setY(tsD.getValue());
			ds.getData().add(cd);
		}
		return ds;	
	}	
}