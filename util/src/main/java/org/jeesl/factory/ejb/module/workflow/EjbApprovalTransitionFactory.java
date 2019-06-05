package org.jeesl.factory.ejb.module.workflow;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalTransitionFactory<AS extends JeeslWorkflowStage<?,?,?,?>,
											AT extends JeeslWorkflowTransition<?,?,AS,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalTransitionFactory.class);
	
	final Class<AT> cTransition;
    
	public EjbApprovalTransitionFactory(final Class<AT> cTransition)
	{       
        this.cTransition = cTransition;
	}
	    
	public AT build(AS source, List<AT> list)
	{
		AT ejb = null;
		try
		{
			ejb = cTransition.newInstance();
			EjbPositionFactory.next(ejb,list);
			ejb.setSource(source);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}