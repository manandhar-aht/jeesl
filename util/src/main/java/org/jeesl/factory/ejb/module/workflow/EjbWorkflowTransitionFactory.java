package org.jeesl.factory.ejb.module.workflow;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowTransitionFactory<WS extends JeeslWorkflowStage<?,?,?,?,?>,
										  WT extends JeeslWorkflowTransition<?,?,WS,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowTransitionFactory.class);
	
	final Class<WT> cTransition;
    
	public EjbWorkflowTransitionFactory(final Class<WT> cTransition)
	{       
        this.cTransition = cTransition;
	}
	    
	public WT build(WS source, List<WT> list)
	{
		WT ejb = null;
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