package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractEjbSecurityFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										 A extends JeeslSecurityAction<L,D,R,V,U,AT>,
										 AT extends JeeslSecurityTemplate<L,D,C>,
										 USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractEjbSecurityFactory.class);
	
    protected final Class<L> cLang;
    protected final Class<D> cDescription;
    protected Class<C> cCategory;
    protected Class<R> cRole;
    protected Class<V> cView;
    protected Class<U> cUsecase;
    protected Class<A> cAction;
    protected Class<AT> cTemplate;
    protected Class<USER> cUser;
	
    public AbstractEjbSecurityFactory(final Class<L> cL, final Class<D> cD)
    {
        this.cLang = cL;
        this.cDescription = cD;
    }
}