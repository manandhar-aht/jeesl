package org.jeesl.factory.ejb.module.approval;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.process.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalStageFactory<P extends JeeslApprovalProcess<?,?,?>,
									AS extends JeeslApprovalStage<?,?,P,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalStageFactory.class);
	
	final Class<AS> cStage;
    
	public EjbApprovalStageFactory(final Class<AS> cStage)
	{       
        this.cStage = cStage;
	}
	    
	public AS build(P process, List<AS> list)
	{
		AS ejb = null;
		try
		{
			ejb = cStage.newInstance();
			EjbPositionFactory.next(ejb,list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}