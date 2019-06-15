package org.jeesl.factory.ejb.module.workflow;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowCommunicationFactory<WT extends JeeslWorkflowTransition<?,?,?,?,SR>,
											C extends JeeslWorkflowCommunication<WT,MT,SR>,
											MT extends JeeslIoTemplate<?,?,?,?,?,?>,
											SR extends JeeslSecurityRole<?,?,?,?,?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowCommunicationFactory.class);
	
	final Class<C> cCommunication;
    
	public EjbWorkflowCommunicationFactory(final Class<C> cCommunication)
	{       
        this.cCommunication = cCommunication;
	}
	    
	public C build(WT transition, List<C> list)
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