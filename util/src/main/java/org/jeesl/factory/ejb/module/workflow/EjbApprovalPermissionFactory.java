package org.jeesl.factory.ejb.module.workflow;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalPermissionFactory<AS extends JeeslWorkflowStage<?,?,?,?>,
											ASP extends JeeslWorkflowStagePermission<AS,?,SR>,
											SR extends JeeslSecurityRole<?,?,?,?,?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalPermissionFactory.class);
	
	private final Class<ASP> cPermission;
    
	public EjbApprovalPermissionFactory(final Class<ASP> cPermission)
	{       
        this.cPermission = cPermission;
	}
	    
	public ASP build(AS stage, List<ASP> list)
	{
		ASP ejb = null;
		try
		{
			ejb = cPermission.newInstance();
			EjbPositionFactory.next(ejb,list);
			ejb.setStage(stage);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}