package org.jeesl.factory.mc.survey;

import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;
import org.jeesl.model.json.JsonFlatFigure;
import org.jeesl.model.json.JsonFlatFigures;
import org.metachart.factory.xml.chart.XmlDataFactory;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class McOptionDataSetFactory <OPTION extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(McOptionDataSetFactory.class);
	
	private final JeeslReportAggregationLevelFactory tfName;
	
	public McOptionDataSetFactory(JeeslReportAggregationLevelFactory tfName)
	{
		this.tfName=tfName;
	}
	
	public DataSet build(JsonFlatFigures figures, List<OPTION> list)
	{
		DataSet dsOption = new DataSet();
		DataSet dsValue = new DataSet();
		Map<Long,OPTION> map = EjbIdFactory.toIdMap(list);
		
        for(JsonFlatFigure f : figures.getFigures())
        {
        		dsOption.getData().add(XmlDataFactory.build(tfName.build(map.get(f.getL2()))));
            dsValue.getData().add(XmlDataFactory.build(f.getL3()));
        }
        
        DataSet ds = new DataSet();
        ds.getDataSet().add(dsOption);
        ds.getDataSet().add(dsValue);

		return ds;
	}
	
}