package net.sf.ahtutils.factory.ejb.security;

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

public abstract class AbstractEjbSecurityFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractEjbSecurityFactory.class);
	
    protected Class<L> cLang;
    protected Class<D> cDescription;
    protected Class<C> cCategory;
    protected Class<R> cRole;
    protected Class<V> cView;
    protected Class<U> cUsecase;
    protected Class<A> cAction;
    protected Class<AT> cTemplate;
    protected Class<USER> cUser;
	
    public AbstractEjbSecurityFactory(final Class<L> cLang,final Class<D> cDescription)
    {
        this.cLang = cLang;
        this.cDescription = cDescription;
    }
    
    public static <L extends UtilsLang, D extends UtilsDescription,
				   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
				   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
				   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
				   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
				   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
				   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
				   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> factoryAction(final Class<L> cLang,final Class<D> cDescription, final Class<A> cAction)
	{
    	return new EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>(cLang,cDescription,cAction);
	}
}