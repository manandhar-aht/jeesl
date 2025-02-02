package org.jeesl.controller.rewrite;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.ocpsoft.rewrite.config.Condition;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractRewriteProvider <L extends UtilsLang, D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											M extends JeeslSecurityMenu<V,M>,
											USER extends JeeslUser<R>>
		extends HttpConfigurationProvider
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractRewriteProvider.class);
	
	protected boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	private JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity;
		
	private U usecase; public U getUsecase(){return usecase;} public void setUsecase(U usecase){this.usecase = usecase;}
	
	protected String forwardDeactivated;
	protected String forwardLogin;
	protected String forwardDenied;
	
	public AbstractRewriteProvider(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,?,USER> fbSecurity)
	{
		debugOnInfo = false;
		forwardDeactivated = "/jsf/settings/system/security/access/pageDeactivated.xhtml";
		forwardLogin = "/jsf/settings/system/security/access/loginRequired.xhtml";
		forwardDenied = "/jsf/settings/system/security/access/denied.xhtml";
	}
	
	public void postConstruct(JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity)
	{
		this.bSecurity=bSecurity;
	}
	
	protected ConfigurationBuilder build(Condition pageActive, Condition notLoggedIn, Condition pageDenied)
	{
		ConfigurationBuilder config = ConfigurationBuilder.begin();
		config = config.addRule(Join.path("/").to("index.jsf"));
		List<V> views = bSecurity.getViews();
		for(V view : views)
		{
			logger.debug("Building Rule for "+view.toString());
			
			config = config.addRule(Join.path(view.getViewPattern()).to(forwardDeactivated)).when(Direction.isInbound().andNot(pageActive));
			config = config.addRule(Join.path(view.getUrlMapping()).to(forwardDeactivated)).when(Direction.isInbound().andNot(pageActive));
			
			config = config.addRule(Join.path(view.getViewPattern()).to(forwardLogin)).when(Direction.isInbound().and(notLoggedIn));
			config = config.addRule(Join.path(view.getUrlMapping()).to(forwardLogin)).when(Direction.isInbound().and(notLoggedIn));
			
			config = config.addRule(Join.path(view.getViewPattern()).to(forwardDenied)).when(Direction.isInbound().and(pageDenied));
			config = config.addRule(Join.path(view.getUrlMapping()).to(forwardDenied)).when(Direction.isInbound().and(pageDenied));
			
			config = config.addRule(Join.path(view.getUrlMapping()).to(view.getViewPattern())).when(Direction.isInbound().and(pageActive));
		}
		logger.info("Rules created for "+views.size()+" Views");
		return config;
	}
/*	
	private static String getUrlMapping1(String context, String url)
	{

		int indexStart = url.indexOf(context);
		int indexParameter = url.indexOf("?");
		
		int indexEnd = url.length();
		if(indexParameter>=0) {indexEnd = indexParameter;}
		
		String httpPattern = url.substring(indexStart+context.length(), indexEnd);
		return httpPattern;
	}
*/	
	public static String getUrlMapping(String context, String urlString)
	{
		try
		{
			URL url = new URL(urlString);
//			System.out.println(url.getProtocol());
//			System.out.println(url.getPath());
			int indexStart = url.getPath().indexOf(context);
			int indexEnd = url.getPath().length();
			return url.getPath().substring(indexStart+context.length(), indexEnd);
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlString;
	}
}