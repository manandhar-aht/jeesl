package org.jeesl.jsf.components;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.jeesl.api.bean.JeeslTrafficLightBean;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

@FacesComponent("org.jeesl.jsf.components.TrafficLight")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TrafficLight extends UIOutput 
{
    final static Logger logger = LoggerFactory.getLogger(TrafficLight.class);
	
    private static enum Attribute {value,scope,style}
	
	@Override public boolean getRendersChildren(){return true;}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		context.getResponseWriter().startElement("div", this);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		JeeslTrafficLightBean appBean = (JeeslTrafficLightBean) context.getApplication().evaluateExpressionGet(context, "#{appTrafficLightsBean}", JeeslTrafficLightBean.class);
		
		Object o =  this.getAttributes().get(Attribute.value.toString());
		Double value = null;
		if(o instanceof Double) {value=(Double)o;}
		else if(o instanceof Integer) {value=((Integer)o).doubleValue();}
		
		if (value!=null)
		{
            String text  = value.toString();
            String color = "";
            String originalStyle = "";
			if (this.getAttributes().containsKey("style"))
			{
				originalStyle = this.getAttributes().get(Attribute.style.toString()).toString();
			}
			
            String style = "text-align: center; " +originalStyle;
            Object scopeAttribute = this.getAttributes().get(Attribute.scope.toString());
            String scope = "";
			if(logger.isTraceEnabled()){logger.info("scope has class " +scopeAttribute.getClass().toString());}
			
			if (scopeAttribute instanceof UtilsStatus)
			{
				UtilsStatus scopeObj = (UtilsStatus) scopeAttribute;
				if(logger.isTraceEnabled()){logger.info("Scope is given as object" +scopeObj.getCode());}
				scope = scopeObj.getCode();
			}
			else
			{
				if(logger.isTraceEnabled()){logger.info("Scope is not given as Object. To String results in " +scopeAttribute.toString());}
				scope = scopeAttribute.toString();
			}


			ArrayList<JeeslTrafficLight> trafficLightDefinitions = (ArrayList<JeeslTrafficLight>) appBean.getTrafficLights(scope);
			if(logger.isTraceEnabled()){logger.info("Loaded " + trafficLightDefinitions.size() + " Traffic Light Definitions from Application Scoped Bean.");}

			for (JeeslTrafficLight trafficLightDefinition : trafficLightDefinitions)
			{
				if(logger.isTraceEnabled()){logger.info("Comparing " +value +">=" +trafficLightDefinition.getThreshold());}
				if (value >= trafficLightDefinition.getThreshold())
				{
					color = trafficLightDefinition.getColorBackground();
					if(logger.isTraceEnabled()){logger.info("true.");}
				}
			}
                    
			if (!color.equals(""))
			{
				 style = style + "background: #" +color +"; ";
			}

			if(logger.isTraceEnabled()){logger.info("Setting style: " + style);}
			
			context.getResponseWriter().writeAttribute("style", style, null);
			context.getResponseWriter().write(text);
		}
	}
	
//	@Override public String getFamily() {return null;}
}