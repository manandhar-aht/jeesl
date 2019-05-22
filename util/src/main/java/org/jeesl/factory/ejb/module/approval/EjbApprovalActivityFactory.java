package org.jeesl.factory.ejb.module.approval;

import java.util.Date;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalActivityFactory<AT extends JeeslApprovalTransition<?,?,?,?>,
										AW extends JeeslApprovalWorkflow<?,?,AY>,
										AY extends JeeslApprovalActivity<AT,AW>

>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalActivityFactory.class);
	
	final Class<AY> cActivity;
    
	public EjbApprovalActivityFactory(final Class<AY> cActivity)
	{       
        this.cActivity = cActivity;
	}
	    
	public AY build(AW workflow, AT transition)
	{
		AY ejb = null;
		try
		{
			ejb = cActivity.newInstance();
			ejb.setWorkflow(workflow);
			ejb.setTransition(transition);
			ejb.setRecord(new Date());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}