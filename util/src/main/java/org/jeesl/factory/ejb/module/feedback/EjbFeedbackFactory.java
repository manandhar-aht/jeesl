package org.jeesl.factory.ejb.module.feedback;

import java.util.Date;

import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class EjbFeedbackFactory<L extends UtilsLang, D extends UtilsDescription,
								FEEDBACK extends JeeslFeedback<L,D,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends UtilsStatus<STYLE,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(EjbFeedbackFactory.class);
	
	private final Class<FEEDBACK> cFeedback;
	
    public EjbFeedbackFactory(final Class<FEEDBACK> cFeedback)
    {
        this.cFeedback = cFeedback;
    } 
    
    public static <L extends UtilsLang, D extends UtilsDescription,
					FEEDBACK extends JeeslFeedback<L,D,FEEDBACK,STYLE,TYPE,USER>,
					STYLE extends UtilsStatus<STYLE,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					USER extends EjbWithEmail>
    	EjbFeedbackFactory<L,D,FEEDBACK,STYLE,TYPE,USER> factory(final Class<FEEDBACK> cFeedback)
    {
        return new EjbFeedbackFactory<L,D,FEEDBACK,STYLE,TYPE,USER>(cFeedback);
    }
	
	public FEEDBACK create(TYPE type, USER user)
	{
		try
		{
			FEEDBACK ejb = cFeedback.newInstance();
			ejb.setType(type);
		    ejb.setUser(user);
		    ejb.setRecord(new Date());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}