package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityArea;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.system.security.SecurityRoleComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAppSecurityBean <L extends UtilsLang,D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											M extends JeeslSecurityMenu<V,M>,
											AR extends JeeslSecurityArea<L,D,V>,
											USER extends JeeslUser<R>>
					implements Serializable,JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppSecurityBean.class);
	
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity;
	protected final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,AR,USER> fbSecurity;

	private List<V> views; @Override public List<V> getViews() {return views;}
	private List<R> roles; public List<R> getRoles() {return roles;}
	
	private final Map<String,V> mapUrlPattern;
	private final Map<String,V> mapUrlMapping;
	private final Map<String,V> mapCodeView;
	private final Map<V,List<R>> mapRoles;
	private final Map<R,List<V>> mapViewsByRole;
	private final Map<R,List<U>> mapUsecasesByRole;
	private final Map<U,List<V>> mapViewsByUsecase;
	private final Map<V,List<A>> mapActionsByView;
	private final Map<R,List<A>> mapActionsByRole;
	private final Map<U,List<A>> mapActionsByUsecase;
	
	private final Comparator<R> cpRole;
	
	private boolean debugOnInfo; protected void setDebugOnInfo(boolean log) {debugOnInfo = log;}

	public AbstractAppSecurityBean(final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,AR,USER> fbSecurity)
	{
		this.fbSecurity=fbSecurity;
		
		mapUrlPattern = new HashMap<String,V>();
		mapUrlMapping = new HashMap<String,V>();
		mapCodeView = new HashMap<String,V>();
		mapRoles = new HashMap<V,List<R>>();
		mapViewsByRole = new HashMap<R,List<V>>();
		mapUsecasesByRole = new HashMap<R,List<U>>();
		mapViewsByUsecase = new HashMap<U,List<V>>();
		
		mapActionsByView = new HashMap<V,List<A>>();
		mapActionsByRole = new HashMap<R,List<A>>();
		mapActionsByUsecase = new HashMap<U,List<A>>();
		
		debugOnInfo = false;
		
		cpRole = (new SecurityRoleComparator<C,R>()).factory(SecurityRoleComparator.Type.position);
	}
	
	public void init(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity)
	{
		this.fSecurity=fSecurity;
		reload();
	}
	
	private void reload()
	{
		views = fSecurity.all(fbSecurity.getClassView());
		for(V v : views){update(v);}
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbSecurity.getClassView(), views));}
		
//		roles = fSecurity.all(fbSecurity.getClassRole());
//		for(R r : roles){update(r);}
//		
//		List<U> usecases = fSecurity.all(fbSecurity.getClassUsecase());
//		for(U u : usecases){update(u);}
	}
	
	public void update(V view)
	{
		if(debugOnInfo) {logger.info("Updating "+JeeslSecurityView.class.getSimpleName()+" "+view.getCode());}
		view = fSecurity.load(fbSecurity.getClassView(), view);
		mapUrlPattern.put(view.getViewPattern(),view);
		mapUrlMapping.put(view.getUrlMapping(),view);
		mapCodeView.put(view.getCode(),view);
		mapActionsByView.put(view,view.getActions());
	}
	
	public void update(R role)
	{
		if(debugOnInfo) {logger.info("Updating "+JeeslSecurityRole.class.getSimpleName()+" "+role.getCode());}
		role = fSecurity.load(fbSecurity.getClassRole(), role);
		mapViewsByRole.put(role,role.getViews());
		mapUsecasesByRole.put(role,role.getUsecases());
		mapActionsByRole.put(role,role.getActions());
	}
	
	public void update(U usecase)
	{
		if(debugOnInfo) {logger.info("Updating "+JeeslSecurityUsecase.class.getSimpleName()+" "+usecase.getCode());}
		usecase = fSecurity.load(fbSecurity.getClassUsecase(), usecase);
		mapViewsByUsecase.put(usecase,usecase.getViews());
		mapActionsByUsecase.put(usecase, usecase.getActions());
	}
	
	@Override public V findViewByCode(String code)
	{
		if(mapCodeView.containsKey(code)) {return mapCodeView.get(code);}
		else {return null;}
	}
	
	@Override public V findViewByHttpPattern(String pattern)
	{
		if(mapUrlPattern.containsKey(pattern)) {return mapUrlPattern.get(pattern);}
		else {return null;}
	}
	
	@Override public V findViewByUrlMapping(String pattern)
	{
		if(mapUrlMapping.containsKey(pattern)) {return mapUrlMapping.get(pattern);}
		else {return null;}
	}

	@Override public List<R> fRoles(V view)
	{
		if(!mapRoles.containsKey(view))
		{
			List<R> list = fSecurity.rolesForView(view);
			Collections.sort(list,cpRole);
			mapRoles.put(view,list);
		}
		return mapRoles.get(view);
	}
	
	@Override public List<V> fViews(R role)
	{
		if(!mapViewsByRole.containsKey(role)){update(role);}
		return mapViewsByRole.get(role);
	}
	@Override public List<V> fViews(U usecase)
	{
		if(!mapViewsByUsecase.containsKey(usecase)){update(usecase);}
		return mapViewsByUsecase.get(usecase);
	}
	@Override public List<U> fUsecases(R role)
	{
		if(!mapUsecasesByRole.containsKey(role)){update(role);}
		return mapUsecasesByRole.get(role);
	}
	
	@Override public List<A> fActions(V view)
	{
		if(!mapActionsByView.containsKey(view)){update(view);}
		return mapActionsByView.get(view);
	}
	@Override public List<A> fActions(R role)
	{
		if(!mapActionsByRole.containsKey(role)){update(role);}
		return mapActionsByRole.get(role);
	}
	@Override public List<A> fActions(U usecase)
	{
		if(!mapActionsByUsecase.containsKey(usecase)){update(usecase);}
		return mapActionsByUsecase.get(usecase);
	}
}