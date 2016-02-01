package net.sf.ahtutils.controller.factory.ejb.tracker;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.tracker.UtilsActionTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbActionTrackerFactory<L extends UtilsLang,
				D extends UtilsDescription,
				C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
				R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
				V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
				U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
				A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
				USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
				T extends UtilsActionTracker<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbActionTrackerFactory.class);
	
    final Class<T> clTracker;
	
    public static <L extends UtilsLang,
				D extends UtilsDescription,
				C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
				R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
				V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
				U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
				A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
				USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
				T extends UtilsActionTracker<L,D,C,R,V,U,A,USER>>
    	EjbActionTrackerFactory<L,D,C,R,V,U,A,USER,T> createFactory(final Class<T> clTracker)
    {
        return new EjbActionTrackerFactory<L,D,C,R,V,U,A,USER,T>(clTracker);
    }
    
    public EjbActionTrackerFactory(final Class<T> clTracker)
    {
        this.clTracker = clTracker;
    } 
    
    public T create(USER user)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTracker.newInstance();
			ejb.setUser(user);
			ejb.setRecord(new Date());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}