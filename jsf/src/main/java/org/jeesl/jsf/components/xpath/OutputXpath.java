package org.jeesl.jsf.components.xpath;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.ComponentAttribute;

@FacesComponent("org.jeesl.jsf.components.xpath.OutputXpath")
@SuppressWarnings("rawtypes")
public class OutputXpath extends AbstractXpath
{	
	final static Logger logger = LoggerFactory.getLogger(OutputXpath.class);
	private static enum Properties {value,xpath,column,styleClass}
		
	@Override public boolean getRendersChildren(){return true;}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("span", this);
		
		writer.writeAttribute("class",ComponentAttribute.get(Properties.styleClass, "", context, this),null);
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
		String xpath = ComponentAttribute.get(Properties.xpath,"",context,this);
				
		ValueExpression ve = this.getValueExpression(Properties.value.toString());
		JXPathContext ctx = JXPathContext.newContext(ve.getValue(context.getELContext()));
		
		StringBuilder sb = new StringBuilder();
		sb.append("");

		try
		{
			Object value = ctx.getValue(xpath);
//			logger.info("Object: Type "+value.getClass().getName());
			
			Object oColumn = ComponentAttribute.getObject(Properties.column,null,context,this);
			if(oColumn!=null && JeeslReportColumn.class.isAssignableFrom(oColumn.getClass()))
			{
				JeeslReportColumn c = (JeeslReportColumn)oColumn;
//				logger.info(c.getClass().getName()+" "+c.toString()+" "+c.getCode());
				if(c.getDataType()!=null)
				{
					UtilsStatus dt = c.getDataType();
//					logger.info("   DataType  code:"+dt.getCode()+" style:"+dt.getStyle()+" symbol:"+dt.getSymbol());
					
					if(dt.getCode().startsWith("numberDouble"))
					{
						DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
						dfs.setDecimalSeparator(',');
						dfs.setGroupingSeparator('.'); 
						
						DecimalFormat df = new DecimalFormat(dt.getSymbol());
						df.setDecimalFormatSymbols(dfs);

						sb.append(df.format(value));
					}
					else if(dt.getCode().equals("date"))
					{
						SimpleDateFormat sdf = super.getSimpleDateFormat(c,dt);
						
//						logger.info("Checking SimpleDateFormat");
						if(value instanceof XMLGregorianCalendar)
						{
							XMLGregorianCalendar xmlGc = (XMLGregorianCalendar)value;
							
							Date d = (Date)xmlGc.toGregorianCalendar().getTime();
							sb.append(sdf.format(d));
						}
						else {logger.warn("Fallback, XMLGC");sb.append(value);}
					}
					else
					{
						// Fallback
						sb.append(value);
					}
				}
				else
				{
					logger.warn("DataType is NULL");
				}
			}
			else
			{
				logger.warn("Fallback, column==null or not assignable");
				sb.append(value);
			}
		}
		catch (JXPathNotFoundException ex){}
		
		context.getResponseWriter().write(sb.toString());
		for(UIComponent uic : this.getChildren())
		{
			uic.encodeAll(context);
		}
	}
}