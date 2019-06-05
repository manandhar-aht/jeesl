package org.jeesl.factory.ejb.module.workflow;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalLinkFactory<RE extends JeeslRevisionEntity<?,?,?,?,?>,
									AL extends JeeslApprovalLink<AW,RE>,
									AW extends JeeslApprovalWorkflow<?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalLinkFactory.class);
	
	final Class<AL> cLink;
    
	public EjbApprovalLinkFactory(final Class<AL> cLink)
	{       
        this.cLink = cLink;
	}
	    
	public AL build(RE entity, AW workflow, JeeslWithWorkflow<AW> object)
	{
		AL ejb = null;
		try
		{
			ejb = cLink.newInstance();
			ejb.setEntity(entity);
			ejb.setWorkflow(workflow);
			ejb.setRefId(object.getId());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}