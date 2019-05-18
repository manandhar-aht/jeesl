package org.jeesl.factory.ejb.module.approval;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbApprovalActionFactory<T extends JeeslApprovalTransition<?,?,?>,
											AA extends JeeslApprovalAction<T,AB,AO,RE,RA>,
											AB extends JeeslApprovalBot<AB,?,?,?>,
											AO extends EjbWithId,
											RE extends JeeslRevisionEntity<?,?,?,?,RA>,
											RA extends JeeslRevisionAttribute<?,?,RE,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalActionFactory.class);
	
	final Class<AA> cAction;
    
	public EjbApprovalActionFactory(final Class<AA> cAction)
	{       
        this.cAction = cAction;
	}
	    
	public AA build(T transition, List<AA> list)
	{
		AA ejb = null;
		try
		{
			ejb = cAction.newInstance();
			EjbPositionFactory.next(ejb,list);
			ejb.setTransition(transition);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}