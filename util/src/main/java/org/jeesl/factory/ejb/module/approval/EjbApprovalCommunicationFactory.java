package org.jeesl.factory.ejb.module.approval;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalCommunicationFactory<T extends JeeslApprovalTransition<?,?,?,?>,
											C extends JeeslApprovalCommunication<T,MT,MR>,
											MT extends JeeslIoTemplate<?,?,?,?,?,?>,
											MR extends JeeslSecurityRole<?,?,?,?,?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalCommunicationFactory.class);
	
	final Class<C> cCommunication;
    
	public EjbApprovalCommunicationFactory(final Class<C> cCommunication)
	{       
        this.cCommunication = cCommunication;
	}
	    
	public C build(T transition, List<C> list)
	{
		C ejb = null;
		try
		{
			ejb = cCommunication.newInstance();
			EjbPositionFactory.next(ejb,list);
			ejb.setTransition(transition);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}