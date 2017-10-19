package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
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

public class AbstractAppSecurityBean <L extends UtilsLang,D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											M extends JeeslSecurityMenu<L,D,C,R,V,U,A,AT,M,USER>,
											USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppSecurityBean.class);
	
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	
	protected Class<C> cCategory;
	protected Class<R> cRole;
	private final Class<V> cView;
	protected Class<U> cUsecase;
	protected Class<A> cAction;
	protected Class<AT> cTemplate;
	protected final Class<USER> cUser;

	private List<V> views; public List<V> getViews() {return views;}
	private final Map<String,V> urlPattern;

	public AbstractAppSecurityBean(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate, final Class<M> cM, final Class<USER> cUser)
	{
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.cUsecase=cUsecase;
		this.cView=cView;
		this.cAction=cAction;
		this.cTemplate=cTemplate;
		this.cUser=cUser;
		
		urlPattern = new HashMap<String,V>();
	}
	
	protected void init(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity)
	{
		this.fSecurity=fSecurity;
		reload();
	}
	
	public void reload()
	{
		views = fSecurity.all(cView);
		for(V v : views)
		{
			urlPattern.put(v.getViewPattern(),v);
		}
		logger.info(views.size()+" "+cView);
	}
	
	public V findView(String pattern)
	{
		if(urlPattern.containsKey(pattern)) {return urlPattern.get(pattern);}
		else {return null;}
	}
	
	public String findCode(String pattern)
	{
		if(urlPattern.containsKey(pattern)) {return urlPattern.get(pattern).getCode();}
		else {return null;}
	}
}