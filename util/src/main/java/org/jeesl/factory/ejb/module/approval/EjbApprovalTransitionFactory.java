package org.jeesl.factory.ejb.module.approval;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalTransitionFactory<S extends JeeslApprovalStage<?,?,?>,
											T extends JeeslApprovalTransition<?,?,S,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalTransitionFactory.class);
	
	final Class<T> cTransition;
    
	public EjbApprovalTransitionFactory(final Class<T> cTransition)
	{       
        this.cTransition = cTransition;
	}
	    
	public T build(S source, List<T> list)
	{
		T ejb = null;
		try
		{
			ejb = cTransition.newInstance();
			EjbPositionFactory.next(ejb,list);
			ejb.setSource(source);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}