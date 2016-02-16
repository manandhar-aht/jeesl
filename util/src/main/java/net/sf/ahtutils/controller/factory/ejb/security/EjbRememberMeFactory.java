package net.sf.ahtutils.controller.factory.ejb.security;

import java.util.UUID;

import org.joda.time.DateTime;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsRememberMe;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class EjbRememberMeFactory <L extends UtilsLang, D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
									REM extends UtilsRememberMe<L,D,C,R,V,U,A,AT,USER>>
{		
	private final Class<REM> cRem;
	
	private EjbRememberMeFactory(final Class<REM> cRem)
	{
		this.cRem=cRem;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
				   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
				   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
				   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
				   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
				   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
				   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
				   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
				   REM extends UtilsRememberMe<L,D,C,R,V,U,A,AT,USER>>
		EjbRememberMeFactory<L,D,C,R,V,U,A,AT,USER,REM> factory(final Class<REM> cRem)
	{
		return new EjbRememberMeFactory<L,D,C,R,V,U,A,AT,USER,REM>(cRem);
	}
	
	public REM create(USER user, int validDays)
	{
		DateTime dt = new DateTime();
		REM ejb = null;
		
		try
    	{
			ejb = cRem.newInstance();
			ejb.setUser(user);
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setValidUntil(dt.plusDays(validDays).toDate());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}