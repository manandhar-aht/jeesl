package net.sf.ahtutils.jsf.handler.security;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.txt.system.security.TxtSecurityActionFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.system.security.SecurityActionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;

public abstract class AbstractJsfSecurityHandler <L extends UtilsLang, D extends UtilsDescription,
													C extends JeeslSecurityCategory<L,D>,
													R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
													V extends JeeslSecurityView<L,D,C,R,U,A>,
													U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
													A extends JeeslSecurityAction<L,D,R,V,U,AT>,
													AT extends JeeslSecurityTemplate<L,D,C>,
													USER extends JeeslUser<R>,
													I extends JeeslIdentity<R,V,U,A,USER>>
								implements UtilsJsfSecurityHandler<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfSecurityHandler.class);
	public static final long serialVersionUID=1;

	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	private JeeslSecurityBean<L,D,C,R,V,U,A,AT,?,USER> bSecurity;
	
	protected I identity;
	
	protected List<A> actions; public List<A> getActions() {return actions;}
	protected List<R> roles; public List<R> getRoles() {return roles;}
	
	protected String pageCode; public String getPageCode() {return pageCode;}
	protected V view; public V getView() {return view;}

	protected TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> txtAction;
	protected Comparator<A> comparatorAction;
	
	protected Map<R,Boolean> mapHasRole;public Map<R,Boolean> getMapHasRole() {return mapHasRole;}
	protected final Map<String,Boolean> mapAllow; public Map<String,Boolean> getMapAllow(){return mapAllow;}
	
	protected boolean noActions; public boolean isNoActions() {return noActions;}
	protected boolean noRoles; public boolean isNoRoles() {return noRoles;}
	
	protected boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	public AbstractJsfSecurityHandler(I identity, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, String pageCode, Class<V> cV)
	{
		this.identity=identity;
		this.fSecurity=fSecurity;
		this.pageCode=pageCode;
		
		debugOnInfo = false;
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

			roles = fSecurity.rolesForView(view);
			noRoles = roles.size()==0;
			update();
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	public AbstractJsfSecurityHandler(I identity, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, JeeslSecurityBean<L,D,C,R,V,U,A,AT,?,USER> bSecurity, V view, Class<V> cV)
	{
		this.identity=identity;
		this.fSecurity=fSecurity;
		this.bSecurity=bSecurity;
		this.view=view;
		this.pageCode=view.getCode();
		
		debugOnInfo = false;
		noActions=true;
		noRoles=true;
		
		mapAllow = new Hashtable<String,Boolean>();
		mapHasRole = new Hashtable<R,Boolean>();
		actions = new ArrayList<A>();
		
		SecurityActionComparator<L,D,C,R,V,U,A,AT,USER> cfAction = new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>();
		cfAction.factory(SecurityActionComparator.Type.position);
		comparatorAction = cfAction.factory(SecurityActionComparator.Type.position);
		
		txtAction = new TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>();
		
		roles = bSecurity.fRoles(view);
		
		noRoles = roles.size()==0;
		update();
	}
	
	private void update()
	{
//		logger.info("AllowedActions:");
		clear();
		for(A action : view.getActions())
		{
			boolean allow = false;
			if(identity!=null){allow=identity.hasAction(action.toCode());}
			addActionWithSecurity(action,allow);
		}
		checkIcon();
	}
	
	protected void clear()
	{
		actions.clear();
		mapAllow.clear();
		mapHasRole.clear();
		for(R r : roles)
		{
			if(identity!=null){mapHasRole.put(r, identity.hasRole(r.getCode()));}
			else{mapHasRole.put(r,false);}
		}
	}

	protected void addActionWithSecurity(A action, boolean allow)
	{
		actions.add(action);
		mapAllow.put(action.toCode(), override(allow));
		logger.trace(action.toString() + " " + allow(action.toCode()));
	}
	
	private boolean override(boolean allow)
	{
		return allow;
//		return true;
	}
	
	protected void checkIcon(){noActions = actions.size() == 0;}
	
	@Override public <E extends Enum<E>> boolean allowSuffixCode(E actionCode)
	{
		return allow(pageCode+"."+actionCode.toString());
	}
	@Override public boolean allow(String actionCode)
	{
		if(actionCode==null){return false;}
		else {return (mapAllow.containsKey(actionCode) && mapAllow.get(actionCode));}
	}
}