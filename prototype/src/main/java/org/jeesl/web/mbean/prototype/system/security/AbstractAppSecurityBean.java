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
	protected final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,USER> fbSecurity;

	private List<V> views; @Override public List<V> getViews() {return views;}
	private final Map<String,V> urlPattern;

	public AbstractAppSecurityBean(final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,USER> fbSecurity)
	{
		this.fbSecurity=fbSecurity;
		
		urlPattern = new HashMap<String,V>();
	}
	
	protected void init(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity)
	{
		this.fSecurity=fSecurity;
		reload();
	}
	
	public void reload()
	{
		views = fSecurity.all(fbSecurity.getClassView());
		for(V v : views)
		{
			urlPattern.put(v.getViewPattern(),v);
		}
		logger.info(AbstractLogMessage.reloaded(fbSecurity.getClassView(), views));
	}
	
	public V findView(String pattern)
	{
		if(urlPattern.containsKey(pattern)) {return urlPattern.get(pattern);}
		else {return null;}
	}
	
	public V findCode(String pattern)
	{
		if(urlPattern.containsKey(pattern)) {return urlPattern.get(pattern);}
		else {return null;}
	}
}