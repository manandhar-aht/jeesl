package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.security.EjbSecurityActionFactory;
import org.jeesl.factory.txt.system.security.TxtStaffFactory;
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
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SecurityFactoryFactory<L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
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
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
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
	
	public <STAFF extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId>
		TxtStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2> txtStaff(String localeCode)
	{
		return new TxtStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2>(localeCode);
	}
	
	
}