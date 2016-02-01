package net.sf.ahtutils.controller.factory.ejb.feedback;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.feedback.UtilsFeedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbFeedbackFactory<L extends UtilsLang,
								D extends UtilsDescription,
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								F extends UtilsFeedback<L,D,C,R,V,U,A,USER,TYPE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbFeedbackFactory.class);
	
	final Class<F> clFeedback;
	
    public EjbFeedbackFactory(final Class<F> clFeedback)
    {
        this.clFeedback = clFeedback;
    } 
    
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					F extends UtilsFeedback<L,D,C,R,V,U,A,USER,TYPE>>
    	EjbFeedbackFactory<L,D,C,R,V,U,A,USER,TYPE,F> factory(final Class<F> clFeedback)
    {
        return new EjbFeedbackFactory<L,D,C,R,V,U,A,USER,TYPE,F>(clFeedback);
    }
	
	public F create(TYPE type, USER user)
	{
		try
		{
			F ejb = clFeedback.newInstance();
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