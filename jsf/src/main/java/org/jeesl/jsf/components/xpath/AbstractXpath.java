package org.jeesl.jsf.components.xpath;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIOutput;

import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractXpath extends UIOutput
{	

	private Map<JeeslReportColumn,SimpleDateFormat> mapDateFormatter;
	
	
	protected SimpleDateFormat getSimpleDateFormat(JeeslReportColumn c, UtilsStatus dt)
	{
		if(mapDateFormatter==null){mapDateFormatter = new HashMap<JeeslReportColumn,SimpleDateFormat>();}
		if(mapDateFormatter.containsKey(c)){return mapDateFormatter.get(c);}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dt.getSymbol());
			mapDateFormatter.put(c,sdf);
			return sdf;
		}
	}
}