package org.jeesl.jsf.components;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

import org.jeesl.interfaces.web.JeeslJsfSecurityHandler;
import org.jeesl.interfaces.web.JeeslJsfWorkflowHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.jsf.util.ComponentAttribute;

@FacesComponent("org.jeesl.jsf.components.Security")
public class Security extends UIPanel
{
	final static Logger logger = LoggerFactory.getLogger(Security.class);
	private boolean debugOnInfo = false;
	
	private static enum Properties {action,handler,allow,workflow}
	private static enum Facets {denied}
	
	@Override public boolean getRendersChildren(){return true;}
	
	private Boolean renderChilds; public Boolean getRenderChilds() {return renderChilds;} public void setRenderChilds(Boolean renderChilds) {this.renderChilds = renderChilds;}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		boolean accessGranted = false;
		boolean workflowAllow = true;
		
		
		if(ComponentAttribute.available(Properties.workflow,context,this))
		{
			
			JeeslJsfWorkflowHandler wfh = (JeeslJsfWorkflowHandler)ComponentAttribute.getObject(Properties.workflow,null,context,this);
			workflowAllow = wfh.isAllowEntityModifications();
			if(debugOnInfo) {logger.info(JeeslJsfWorkflowHandler.class.getSimpleName()+" evaluated workflowAllow:"+workflowAllow);}
		}
//		ValueExpression veWorkflow = this.getValueExpression(Properties.workflow.toString());
//		if(veWorkflow!=null)
//		{
//			JeeslJsfWorkflowHandler wfh = (JeeslJsfWorkflowHandler)veWorkflow.getValue(context.getELContext());
//			
//			workflowAllow = wfh.isAllowEntityModifications();
//		}
		
		
		boolean accessGrantedAttribute = ComponentAttribute.getBoolean(Properties.allow,true,context,this);
		try
		{
			ValueExpression ve = this.getValueExpression(Properties.handler.toString());
			if(ve==null){throw new UtilsNotFoundException("");}
			JeeslJsfSecurityHandler<?,?,?,?,?,?> handler = (JeeslJsfSecurityHandler<?,?,?,?,?,?>)ve.getValue(context.getELContext());
			
			String action = ComponentAttribute.get(Properties.action.toString(),context,this);
			accessGranted = (handler.allow(action) && accessGrantedAttribute);
			if(debugOnInfo) {logger.info(JeeslJsfSecurityHandler.class.getSimpleName()+" evaluated accessGranted:"+accessGranted);}
		}
		catch (UtilsNotFoundException e)
		{
			accessGranted = accessGrantedAttribute;
			if(debugOnInfo) {logger.info("No "+JeeslJsfSecurityHandler.class.getSimpleName()+", so accessGranted:"+accessGranted);}
		}
		
		if(debugOnInfo) {logger.info("Final: accessGranted:"+accessGranted+" workflowAllow:"+workflowAllow);}
			
		if(accessGranted && workflowAllow)
		{
			for(UIComponent uic : this.getChildren())
			{
				uic.encodeAll(context);
			}
		}
		else if(this.getFacets().containsKey(Facets.denied.toString()))
		{
			this.getFacet(Facets.denied.toString()).encodeAll(context);
		}
	}
}