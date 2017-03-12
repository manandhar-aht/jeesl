package net.sf.ahtutils.factory.xml.mc;

import java.util.List;

import org.jeesl.model.xml.module.ts.TimeSeries;
import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsBridge;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;
import net.sf.exlp.util.DateUtil;

public class XmlMcDataSetFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMcDataSetFactory.class);

	
	public static DataSet build(TimeSeries timeSeries)
	{
		DataSet ds = new DataSet();
		
		for(org.jeesl.model.xml.module.ts.Data tsD: timeSeries.getData())
		{
			Data cd = new Data();
			cd.setRecord(tsD.getRecord());
			cd.setY(tsD.getValue());
			ds.getData().add(cd);
		}
		return ds;	
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					BRIDGE extends UtilsTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					WS extends UtilsStatus<WS,L,D>,
					QAF extends UtilsStatus<QAF,L,D>>
	DataSet build(List<DATA> datas)
	{
		DataSet ds = new DataSet();
		
		for(DATA data: datas)
		{
			Data cd = new Data();
			cd.setRecord(DateUtil.toXmlGc(data.getRecord()));
			cd.setY(data.getValue());
			ds.getData().add(cd);
		}
		return ds;	
	}
}