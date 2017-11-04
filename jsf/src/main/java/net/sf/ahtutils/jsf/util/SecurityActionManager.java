package net.sf.ahtutils.jsf.util;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class SecurityActionManager <L extends UtilsLang,
									D extends UtilsDescription,
									C extends JeeslSecurityCategory<L,D>,
									R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends JeeslSecurityView<L,D,C,R,U,A>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
									A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityActionManager.class);
	
	public static <L extends UtilsLang,
		   D extends UtilsDescription, 
		   C extends JeeslSecurityCategory<L,D>,
		   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
		   V extends JeeslSecurityView<L,D,C,R,U,A>,
		   U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
		   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
		   AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
		   USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
		SecurityActionManager<L,D,C,R,V,U,A,AT,USER>
		factory(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity,final Class<V> cView, String viewId, JeeslIdentity<L,D,C,R,V,U,A,AT,USER> identity) throws UtilsNotFoundException
	{
		return new SecurityActionManager<L,D,C,R,V,U,A,AT,USER>(fSecurity,cView,viewId,identity);
	}
	
	private Map<String,Boolean> allowed;
	
	public SecurityActionManager(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, final Class<V> cView, String viewId, JeeslIdentity<L,D,C,R,V,U,A,AT,USER> identity) throws UtilsNotFoundException
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