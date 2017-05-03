package org.jeesl.jsf.components;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.ComponentAttribute;

@FacesComponent("org.jeesl.jsf.components.OutputXpath")
public class OutputXpath extends UIOutput
{	
	final static Logger logger = LoggerFactory.getLogger(OutputXpath.class);
	private static enum Properties {styleClass,value,xpath,column}
	
	@Override public boolean getRendersChildren(){return true;}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("span", this);
		
		writer.writeAttribute("class",ComponentAttribute.get(Properties.styleClass.toString(), "", context, this),null);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("span");
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		ResponseWriter writer = context.getResponseWriter();
		
		String xpath = ComponentAttribute.get(Properties.xpath.toString(),"",context,this);
				
		ValueExpression ve = this.getValueExpression(Properties.value.toString());
		JXPathContext ctx = JXPathContext.newContext(ve.getValue(context.getELContext()));
		
		StringBuilder sb = new StringBuilder();
		sb.append("");
			
		try
		{
			Object oColumn = ComponentAttribute.getObject(Properties.column.toString(),null,context,this);
			if(oColumn!=null && JeeslReportColumn.class.isAssignableFrom(oColumn.getClass()))
			{
				JeeslReportColumn c = (JeeslReportColumn)oColumn;
//				logger.info(c.getClass().getName()+" "+c.toString()+" "+c.getCode());
				if(c.getDataType()!=null)
				{
					UtilsStatus dt = c.getDataType();
//					logger.info("   "+dt.getCode()+" "+dt.getStyle()+" "+dt.getSymbol());
					
					if(dt.getCode().startsWith("numberDouble"))
					{
						DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
						dfs.setDecimalSeparator(',');
						dfs.setGroupingSeparator('.'); 
						
						DecimalFormat df = new DecimalFormat(dt.getSymbol());
						df.setDecimalFormatSymbols(dfs);

						sb.append(df.format(ctx.getValue(xpath)));
					}
					else
					{
						// Fallback
						sb.append(ctx.getValue(xpath));
					}
				}
			}
			else
			{
				// Fallback
				sb.append(ctx.getValue(xpath));
			}
		}
		catch (JXPathNotFoundException ex){}
		
		
		
		writer.write(sb.toString());
		
		for(UIComponent uic : this.getChildren())
		{
			uic.encodeAll(context);
		}
	}
}