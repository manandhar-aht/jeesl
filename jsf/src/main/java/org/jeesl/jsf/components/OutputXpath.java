package org.jeesl.jsf.components;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.jxpath.JXPathContext;

import net.sf.ahtutils.jsf.util.ComponentAttribute;

@FacesComponent("org.jeesl.jsf.components.OutputXpath")
public class OutputXpath extends UIOutput
{	
	private static enum Properties {styleClass,value,xpath}
	
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
		
		String xpath = ComponentAttribute.get(Properties.xpath.toString(), "", context, this);
		
		ValueExpression ve = this.getValueExpression(Properties.value.toString());
		JXPathContext ctx = JXPathContext.newContext(ve.getValue(context.getELContext()));
				
		writer.write(""+ctx.getValue(xpath));
		
		for(UIComponent uic : this.getChildren())
		{
			uic.encodeAll(context);
		}
	}
}