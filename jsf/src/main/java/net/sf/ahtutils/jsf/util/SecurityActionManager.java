package net.sf.ahtutils.jsf.util;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.interfaces.facade.JeeslSecurityFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsIdentity;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class SecurityActionManager <L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityActionManager.class);
	
	public static <L extends UtilsLang,
		   D extends UtilsDescription, 
		   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
		   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
		   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
		   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
		   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
		   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
		   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		SecurityActionManager<L,D,C,R,V,U,A,AT,USER>
		factory(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity,final Class<V> cView, String viewId, UtilsIdentity<L,D,C,R,V,U,A,AT,USER> identity) throws UtilsNotFoundException
	{
		return new SecurityActionManager<L,D,C,R,V,U,A,AT,USER>(fSecurity,cView,viewId,identity);
	}
	
	private Map<String,Boolean> allowed;
	
	public SecurityActionManager(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, final Class<V> cView, String viewId, UtilsIdentity<L,D,C,R,V,U,A,AT,USER> identity) throws UtilsNotFoundException
	{
		allowed = new Hashtable<String,Boolean>();
		V view = fSecurity.fByCode(cView,viewId);
		view = fSecurity.load(cView, view);
		for(A a : view.getActions())
		{
			allowed.put(a.getCode(), identity.hasAction(a.getCode()));
		}
	}
	
	public Map<String, Boolean> getAllowed() {return allowed;}
}