package org.jeesl.jsf.components;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.jeesl.api.bean.JeeslTrafficLightBean;
import org.jeesl.factory.css.CssColorFactory;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.jsf.util.TrafficLightProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

@FacesComponent("org.jeesl.jsf.components.TrafficLight")
public class TrafficLight <L extends UtilsLang,D extends UtilsDescription,
							LIGHT extends JeeslTrafficLight<L,D,SCOPE>,SCOPE extends UtilsStatus<SCOPE,L,D>>
							extends UIOutput 
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
		@SuppressWarnings("unchecked")
		JeeslTrafficLightBean<L,D,LIGHT,SCOPE> appBean = (JeeslTrafficLightBean<L,D,LIGHT,SCOPE>) context.getApplication().evaluateExpressionGet(context, "#{appTrafficLightsBean}", JeeslTrafficLightBean.class);
		
		Object o =  this.getAttributes().get(Attribute.value.toString());
		Double value = null;
		if(o instanceof Double) {value=(Double)o;}
		else if(o instanceof Integer) {value=((Integer)o).doubleValue();}
		
		if (value!=null)
		{
            StringBuilder sb = new StringBuilder();
			if (this.getAttributes().containsKey("style"))
			{
				sb.append(this.getAttributes().get(Attribute.style.toString()).toString());
			}
			sb.append(" text-align: center;");
           
            String scope = TrafficLightProcessor.findScope(this.getAttributes().get(Attribute.scope.toString()));			
			CssColorFactory.addColor(sb, TrafficLightProcessor.findLight(appBean.getTrafficLights(scope), value));
			
			context.getResponseWriter().writeAttribute("style", sb.toString(), null);
			context.getResponseWriter().write(value.toString());
		}
	}
}