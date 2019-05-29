package org.jeesl.factory.ejb.module.approval;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalWorkflowFactory<AP extends JeeslApprovalProcess<?,?,?>,
										AS extends JeeslApprovalStage<?,?,AP,?>,
										AW extends JeeslApprovalWorkflow<AP,AS,?>

>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalWorkflowFactory.class);
	
	final Class<AW> cWorkflow;
    
	public EjbApprovalWorkflowFactory(final Class<AW> cWorkflow)
	{       
        this.cWorkflow = cWorkflow;
	}
	    
	public AW build(AP process)
	{
		AW ejb = null;
		try
		{
			ejb = cWorkflow.newInstance();
			ejb.setProcess(process);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}