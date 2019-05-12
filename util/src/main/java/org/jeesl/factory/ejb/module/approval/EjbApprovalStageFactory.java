package org.jeesl.factory.ejb.module.approval;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalStageFactory<P extends JeeslApprovalProcess<?,?,?>,
									S extends JeeslApprovalStage<?,?,P>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalStageFactory.class);
	
	final Class<S> cStage;
    
	public EjbApprovalStageFactory(final Class<S> cStage)
	{       
        this.cStage = cStage;
	}
	    
	public S build(P process, List<S> list)
	{
		S ejb = null;
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