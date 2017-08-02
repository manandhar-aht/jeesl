package org.jeesl.jsf.components.xpath;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIOutput;

import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractXpath extends UIOutput
{	
	private Map<JeeslReportColumn,SimpleDateFormat> mapDateFormatter;
	private Map<String,DecimalFormat> mapDecimal;
	
	@Override public boolean getRendersChildren(){return true;}
	
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
	
	protected DecimalFormat getDecimalFormat(String pattern)
	{
		if(mapDecimal==null){mapDecimal = new HashMap<String,DecimalFormat>();}
		if(mapDecimal.containsKey(pattern)){return mapDecimal.get(pattern);}
		else
		{
			DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
			dfs.setDecimalSeparator(',');
			dfs.setGroupingSeparator('.'); 
			
			DecimalFormat df = new DecimalFormat(pattern);
			df.setDecimalFormatSymbols(dfs);
			mapDecimal.put(pattern,df);
			return df;
		}
	}
}