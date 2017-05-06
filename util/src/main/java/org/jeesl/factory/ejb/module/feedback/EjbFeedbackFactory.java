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
								USER extends EjbWithEmail,
								TYPE extends UtilsStatus<TYPE,L,D>,
								F extends JeeslFeedback<L,D,USER,TYPE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbFeedbackFactory.class);
	
	private final Class<F> cFeedback;
	
    public EjbFeedbackFactory(final Class<F> cFeedback)
    {
        this.cFeedback = cFeedback;
    } 
    
    public static <L extends UtilsLang, D extends UtilsDescription,
					USER extends EjbWithEmail,
					TYPE extends UtilsStatus<TYPE,L,D>,
					F extends JeeslFeedback<L,D,USER,TYPE>>
    	EjbFeedbackFactory<L,D,USER,TYPE,F> factory(final Class<F> clFeedback)
    {
        return new EjbFeedbackFactory<L,D,USER,TYPE,F>(clFeedback);
    }
	
	public F create(TYPE type, USER user)
	{
		try
		{
			F ejb = cFeedback.newInstance();
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