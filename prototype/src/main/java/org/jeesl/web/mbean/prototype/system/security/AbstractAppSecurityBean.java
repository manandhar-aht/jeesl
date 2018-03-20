package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
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
											USER extends JeeslUser<R>>
					implements Serializable,JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppSecurityBean.class);
	
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	protected final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity;

	private List<V> views; @Override public List<V> getViews() {return views;}
	
	private final Map<String,V> mapUrlPattern;
	private final Map<String,V> mapUrlMapping;
	private final Map<String,V> mapCodeView;
	private final Map<V,List<R>> mapRoles;

	public AbstractAppSecurityBean(final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity)
	{
		this.fbSecurity=fbSecurity;
		
		mapUrlPattern = new HashMap<String,V>();
		mapUrlMapping = new HashMap<String,V>();
		mapCodeView = new HashMap<String,V>();
		mapRoles = new HashMap<V,List<R>>();
	}
	
	protected void init(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity)
	{
		this.fSecurity=fSecurity;
		reload();
	}
	
	private void reload()
	{
		views = fSecurity.all(fbSecurity.getClassView());
		for(V v : views)
		{
			update(v);
		}
		logger.info(AbstractLogMessage.reloaded(fbSecurity.getClassView(), views));
	}
	
	public void update(V view)
	{
		logger.info("Updating "+view.getCode());
		mapUrlPattern.put(view.getViewPattern(),view);
		mapUrlMapping.put(view.getUrlMapping(),view);
		mapCodeView.put(view.getCode(),view);
	}
	
	public void update(R role)
	{

	}
	
	public void update(U usecase)
	{

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

	@Override
	public List<R> fRoles(V view)
	{
		if(!mapRoles.containsKey(view))
		{
			mapRoles.put(view,fSecurity.rolesForView(view));
		}
		
		return mapRoles.get(view);
	}
}