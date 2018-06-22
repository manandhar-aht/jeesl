package org.jeesl.factory.ejb.module.approval;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalProcessFactory<PROCESS extends JeeslApprovalProcess<?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalProcessFactory.class);
	
	final Class<PROCESS> cProcess;
    
	public EjbApprovalProcessFactory(final Class<PROCESS> cProcess)
	{       
        this.cProcess = cProcess;
	}
	    
	public PROCESS build()
	{
		PROCESS ejb = null;
		try
		{
			ejb = cProcess.newInstance();
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}