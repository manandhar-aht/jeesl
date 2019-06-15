package org.jeesl.jsf.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.txt.system.security.TxtSecurityActionFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityArea;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.web.JeeslJsfSecurityHandler;
import org.jeesl.util.comparator.ejb.system.security.SecurityActionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractJsfSecurityHandler <L extends UtilsLang, D extends UtilsDescription,
													C extends JeeslSecurityCategory<L,D>,
													R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
													V extends JeeslSecurityView<L,D,C,R,U,A>,
													U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
													A extends JeeslSecurityAction<L,D,R,V,U,AT>,
													AT extends JeeslSecurityTemplate<L,D,C>,
													AR extends JeeslSecurityArea<L,D,V>,
													USER extends JeeslUser<R>,
													I extends JeeslIdentity<R,V,U,A,USER>>
								implements JeeslJsfSecurityHandler<R,V,U,A,AR,USER>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfSecurityHandler.class);
	public static final long serialVersionUID=1;

	private SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,?,?,USER> fbSecurity;
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,?,USER> fSecurity;
	protected JeeslSecurityBean<L,D,C,R,V,U,A,AT,?,USER> bSecurity;
	
	protected I identity;
	
	protected List<R> roles; public List<R> getRoles() {return roles;}
	protected final List<A> actions; public List<A> getActions() {return actions;}
	private final List<AR> areas; public List<AR> getAreas() {return areas;}
	
	protected String pageCode; public String getPageCode() {return pageCode;}
	protected V view; public V getView() {return view;}

	protected TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> txtAction;
	protected Comparator<A> comparatorAction;
	
	protected Map<R,Boolean> mapHasRole; @Override public Map<R,Boolean> getMapHasRole() {return mapHasRole;}
	protected final Map<String,Boolean> mapAllow; public Map<String,Boolean> getMapAllow(){return mapAllow;}
	
	protected boolean noActions; public boolean isNoActions() {return noActions;}
	protected boolean noRoles; public boolean isNoRoles() {return noRoles;}
	
	protected boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	public AbstractJsfSecurityHandler(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,?,?,USER> fbSecurity,
									I identity,
									JeeslSecurityFacade<L,D,C,R,V,U,A,AT,?,USER> fSecurity,
									String pageCode)
	{
		this.fbSecurity=fbSecurity;
		this.identity=identity;
		this.fSecurity=fSecurity;
		this.pageCode=pageCode;
		
		debugOnInfo = false;
		noActions=true;
		noRoles=true;
		
		mapAllow = new Hashtable<String,Boolean>();
		mapHasRole = new Hashtable<R,Boolean>();
		actions = new ArrayList<A>();
		areas = new ArrayList<AR>();
		
		SecurityActionComparator<L,D,C,R,V,U,A,AT,USER> cfAction = new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>();
		cfAction.factory(SecurityActionComparator.Type.position);
		comparatorAction = cfAction.factory(SecurityActionComparator.Type.position);
		
		txtAction = new TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>();
		
		try
		{
			view = fSecurity.fByCode(fbSecurity.getClassView(),pageCode);
			view = fSecurity.load(fbSecurity.getClassView(), view);

			roles = fSecurity.rolesForView(view);
			noRoles = roles.size()==0;
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	public AbstractJsfSecurityHandler(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,?,?,USER> fbSecurity,
										JeeslSecurityFacade<L,D,C,R,V,U,A,AT,?,USER> fSecurity,
										JeeslSecurityBean<L,D,C,R,V,U,A,AT,?,USER> bSecurity,
										I identity,
										String viewCode)
	{
		this.fbSecurity=fbSecurity;
		this.identity=identity;
		this.fSecurity=fSecurity;
		this.bSecurity=bSecurity;
		
		this.view=bSecurity.findViewByCode(viewCode);
		this.pageCode=view.getCode();
		
		debugOnInfo = false;
		noActions=true;
		noRoles=true;
		
		mapAllow = new Hashtable<String,Boolean>();
		mapHasRole = new Hashtable<R,Boolean>();
		actions = new ArrayList<A>();
		areas = new ArrayList<AR>();
		
		SecurityActionComparator<L,D,C,R,V,U,A,AT,USER> cfAction = new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>();
		cfAction.factory(SecurityActionComparator.Type.position);
		comparatorAction = cfAction.factory(SecurityActionComparator.Type.position);
		
		txtAction = new TxtSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>();
		
		roles = bSecurity.fRoles(view);
		
		noRoles = roles.size()==0;
		update();
	}
	
	protected void update()
	{
		clear();	
		if(debugOnInfo) {logger.info("Checking Assignment of "+view.getActions()+" "+fbSecurity.getClassAction().getSimpleName()+" for user");}
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
		areas.clear();
		
		mapAllow.clear();
		mapHasRole.clear();
		
		if(debugOnInfo) {logger.info("Checking Assignment of "+roles.size()+" "+fbSecurity.getClassRole().getSimpleName()+" for user");}
		for(R r : roles)
		{
			StringBuilder sb = null;
			if(debugOnInfo)
			{
				sb = new StringBuilder();
				sb.append("\t").append(r.getCode()).append(": ");
			}
			if(identity!=null)
			{
				boolean b = identity.hasRole(r.getCode());
				if(debugOnInfo) {sb.append(b);}
				mapHasRole.put(r,b);
			}
			else
			{
				if(debugOnInfo) {sb.append(" false (identity is null)");}
				mapHasRole.put(r,false);
			}
			if(debugOnInfo) {logger.info(sb.toString());}
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
	
	@Override public boolean hasRole(R role) {return mapHasRole.containsKey(role) && mapHasRole.get(role);}
	
	protected boolean hasDomainRole(A action, Collection<R> staffRoles)
	{
		boolean allowDomain = false;
		for(R r : staffRoles)
		{
			List<A> lA1 = new ArrayList<>();
			if(bSecurity==null)
			{
				r = fSecurity.load(fbSecurity.getClassRole(), r);
				lA1.addAll(r.getActions());
			}
			else {lA1.addAll(bSecurity.fActions(r));}
			
			if(lA1.contains(action)){allowDomain=true;}
			else
			{
				List<U> usecases = new ArrayList<>();
				if(bSecurity==null) {usecases.addAll(r.getUsecases());}
				else {usecases.addAll(bSecurity.fUsecases(r));}
				for(U uc : usecases)
				{
					List<A> lA2 = new ArrayList<>();
					if(bSecurity==null)
					{
						uc = fSecurity.load(fbSecurity.getClassUsecase(), uc);
						lA2.addAll(uc.getActions());
					}
					else {lA2.addAll(bSecurity.fActions(uc));}
					
					if(lA2.contains(action)){allowDomain=true;}
				}
			}
		}
		return allowDomain;
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
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			logger.info("Debugging SecurityHandler ("+pageCode+")");
			for(String key : mapAllow.keySet())
			{
				logger.info("\t"+key+": "+mapAllow.get(key));
			}
			for(A a : actions)
			{
				logger.info("\t"+a.toString());
			}
			for(AR area : areas)
			{
				logger.info("\tAR\t"+area.toString());
			}
		}
	}
	
	protected void updateActionsForDomainRoles(List<R> staffRoles)
	{
		for(A action : view.getActions())
		{
			boolean allowSystem = identity.hasAction(action.toCode());
			boolean allowDomain = hasDomainRole(action,staffRoles);

			boolean allow = allowSystem || allowDomain;
			addActionWithSecurity(action, allow);
		}
		checkIcon();
	}
}