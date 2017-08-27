package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.security.EjbSecurityActionFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
import org.jeesl.factory.txt.system.security.TxtStaffFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.util.JeeslStaff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SecurityFactoryFactory<L extends UtilsLang, D extends UtilsDescription,
									C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityFactoryFactory.class);
    
    private final Class<L> cL;
    private final Class<D> cD;
    
    private final Class<A> cAction;
	
	public SecurityFactoryFactory(final Class<L> cL, final Class<D> cD, final Class<A> cAction)
	{		
		this.cL=cL;
		this.cD=cD;
		
		this.cAction=cAction;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
					R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
					U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
					A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		SecurityFactoryFactory<L,D,C,R,V,U,A,AT,USER>
		factory(final Class<L> cL, final Class<D> cD, final Class<A> cAction)
	{
		return new SecurityFactoryFactory<L,D,C,R,V,U,A,AT,USER>(cL,cD,cAction);
	}
	
	public EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> ejbAction()
	{
		return new EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>(cL,cD,cAction);
	}
	
	public <M extends JeeslSecurityMenu<L,D,C,R,V,U,A,AT,M,USER>> EjbSecurityMenuFactory<L,D,C,R,V,U,A,AT,M,USER> ejbMenu(Class<M> cM)
	{
		return new EjbSecurityMenuFactory<L,D,C,R,V,U,A,AT,M,USER>(cL,cD,cM);
	}
	
	public <STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId>
		TxtStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2> txtStaff(String localeCode)
	{
		return new TxtStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2>(localeCode);
	}
	
	
}