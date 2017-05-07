package org.jeesl.factory.ejb.module.feedback;

import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class EjbFeedbackThreadFactory<L extends UtilsLang, D extends UtilsDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends UtilsStatus<STYLE,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(EjbFeedbackThreadFactory.class);
	
	private final Class<THREAD> cThread;
	
    public EjbFeedbackThreadFactory(final Class<THREAD> cThread)
    {
        this.cThread = cThread;
    } 
	
	public THREAD build()
	{
		try
		{
			THREAD ejb = cThread.newInstance();

		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}