package net.sf.ahtutils.controller.factory.ejb.tracker;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.tracker.UtilsActionTracker;

public class EjbActionTrackerFactory<L extends UtilsLang,
				D extends UtilsDescription,
				C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
				R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
				V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
				U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
				A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
				AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
				USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
				T extends UtilsActionTracker<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbActionTrackerFactory.class);
	
    final Class<T> clTracker;
	
    public static <L extends UtilsLang,
				D extends UtilsDescription,
				C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
				R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
				V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
				U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
				A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
				AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
				USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
				T extends UtilsActionTracker<L,D,C,R,V,U,A,AT,USER>>
    	EjbActionTrackerFactory<L,D,C,R,V,U,A,AT,USER,T> createFactory(final Class<T> clTracker)
    {
        return new EjbActionTrackerFactory<L,D,C,R,V,U,A,AT,USER,T>(clTracker);
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