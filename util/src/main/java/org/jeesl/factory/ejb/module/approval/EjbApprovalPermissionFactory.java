package org.jeesl.factory.ejb.module.approval;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStagePermission;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalPermissionFactory<AS extends JeeslApprovalStage<?,?,?,?>,
											ASP extends JeeslApprovalStagePermission<AS,?,SR>,
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