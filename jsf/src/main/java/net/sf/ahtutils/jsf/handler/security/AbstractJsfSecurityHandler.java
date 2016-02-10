package net.sf.ahtutils.jsf.handler.security;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.txt.security.TxtSecurityActionFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
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
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityActionComparator;

public abstract class AbstractJsfSecurityHandler <L extends UtilsLang,
													D extends UtilsDescription,
													C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
													R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
													V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
													U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
													A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
													AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
													USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
													I extends UtilsIdentity<L,D,C,R,V,U,A,AT,USER>>
	implements UtilsJsfSecurityHandler
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfSecurityHandler.class);
	public static final long serialVersionUID=1;

	protected UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	private I identity;
	
	protected List<A> actions; public List<A> getActions() {return actions;}
	protected List<R> roles; public List<R> getRoles() {return roles;}
	
	protected String pageCode;
	protected V view;public V getView() {return view;}

	protected TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> txtAction;
	protected Comparator<A> comparatorAction;
	
	protected Map<R,Boolean> mapHasRole;public Map<R, Boolean> getMapHasRole() {return mapHasRole;}
	protected Map<String,Boolean> mapAllow; public Map<String, Boolean> getMapAllow(){return mapAllow;}
	
	protected boolean noActions; public boolean isNoActions() {return noActions;}
	protected boolean noRoles; public boolean isNoRoles() {return noRoles;}
	
	public AbstractJsfSecurityHandler(UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, String pageCode, Class<V> cV)
	{
		this.fSecurity=fSecurity;
		this.pageCode=pageCode;
		
		noActions=true;
		noRoles=true;
		
		mapAllow = new Hashtable<String,Boolean>();
		mapHasRole = new Hashtable<R,Boolean>();
		actions = new ArrayList<A>();
		
		SecurityActionComparator<L,D,C,R,V,U,A,AT,USER> cfAction = new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>();
		cfAction.factory(SecurityActionComparator.Type.position);
		comparatorAction = cfAction.factory(SecurityActionComparator.Type.position);
		
		txtAction = new TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>();
		
		try
		{
			view = fSecurity.fByCode(cV, pageCode);
			view = fSecurity.load(cV, view);
//			Collections.sort(actions, comparatorAction);

			roles = fSecurity.rolesForView(cV, view);
			noRoles = roles.size()==0;
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	protected void addActionWithSecurity(A action, boolean allow)
	{
		actions.add(action);
		mapAllow.put(action.getCode(), override(allow));
		logger.trace(action.toString() + " " + allow(action.getCode()));
	}
	
	private boolean override(boolean allow)
	{
		return allow;
//		return true;
	}
	
	protected void checkIcon(){noActions = actions.size() == 0;}
	
	@Override
	public boolean allow(String actionCode)
	{
		if(actionCode==null){return false;}
		else {return (mapAllow.containsKey(actionCode) && mapAllow.get(actionCode));}
	}
}