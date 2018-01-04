package org.jeesl.web.mbean.prototype.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslMenuBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
import org.jeesl.factory.txt.system.security.TxtSecurityMenuFactory;
import org.jeesl.factory.xml.system.navigation.XmlBreadcrumbFactory;
import org.jeesl.factory.xml.system.navigation.XmlMenuFactory;
import org.jeesl.factory.xml.system.navigation.XmlMenuItemFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.system.navigation.Breadcrumb;
import org.jeesl.model.xml.system.navigation.Menu;
import org.jeesl.model.xml.system.navigation.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class PrototypeDb2MenuBean <L extends UtilsLang, D extends UtilsDescription,
									C extends JeeslSecurityCategory<L,D>,
									R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
									V extends JeeslSecurityView<L,D,C,R,U,A>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
									A extends JeeslSecurityAction<L,D,R,V,U,AT>,
									AT extends JeeslSecurityTemplate<L,D,C>,
									M extends JeeslSecurityMenu<V,M>,
									USER extends JeeslUser<R>,
									I extends JeeslIdentity<R,V,U,A,USER>>
		implements Serializable,JeeslMenuBean<L,D,R,V,U,A,M,USER,I>
{
	final static Logger logger = LoggerFactory.getLogger(PrototypeDb2MenuBean.class);
	private static final long serialVersionUID = 1L;
	
	private final Class<M> cMenu;
	
	private JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	
	private final XmlMenuItemFactory<L,D,C,R,V,U,A,AT,M,USER> xfMenuItem;
	private final EjbSecurityMenuFactory<V,M> efMenu;
	private final TxtSecurityMenuFactory<L,D,C,R,V,U,A,AT,M,USER> tfMenu;
	
	private final Map<String,M> mapKey;
	private final Map<M,List<M>> mapChild;
	private final Map<M,Menu> mapMenu;
	private final Map<M,MenuItem> mapSub;
	private final Map<M,Breadcrumb> mapBreadcrumb;

	private I identity;
	private String localeCode;
	private boolean setupRequired=false;
	private boolean debugOnInfo; protected void setLogInfo(boolean log) {debugOnInfo = log;}

	public PrototypeDb2MenuBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,USER> fbSecurity, final Class<M> cMenu)
	{
		this.cMenu=cMenu;
		
		mapKey = new HashMap<String,M>();
		mapMenu = new HashMap<M,Menu>();
		mapChild = new HashMap<M,List<M>>();
		mapSub = new HashMap<M,MenuItem>();
		mapBreadcrumb = new HashMap<M,Breadcrumb>();
		
		xfMenuItem = new XmlMenuItemFactory<L,D,C,R,V,U,A,AT,M,USER>(localeCode);
		efMenu = fbSecurity.ejbMenu(cMenu);
		tfMenu = new TxtSecurityMenuFactory<L,D,C,R,V,U,A,AT,M,USER>();
		
		debugOnInfo = false;
		setupRequired = false;
	}
	
	public void initSuper(String localeCode, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, I identity)
	{
		this.fSecurity=fSecurity;
		prepare(localeCode, identity);
	}
	
	public void prepare(String localeCode, I identity)
	{
		this.localeCode=localeCode;
		this.identity=identity;
		reset();
	}
	
	public void reset()
	{
		mapKey.clear();
		mapMenu.clear();
		mapChild.clear();
		mapSub.clear();
		mapBreadcrumb.clear();
		setupRequired = true;
	}
	
	private synchronized void setup()
	{
		if(setupRequired)
		{
			if(debugOnInfo) {logger.info(StringUtil.stars());logger.info("Setup Menu");}
			xfMenuItem.setLocaleCode(localeCode);
			
			M root = efMenu.build();
			mapKey.put(JeeslSecurityMenu.keyRoot, root);
			for(M m : fSecurity.allOrderedPosition(cMenu))
			{
				if(debugOnInfo)
				{
					logger.info("View: "+m.getView().getCode());
					logger.info("\t\tm.getView().isVisible() "+m.getView().isVisible());
					logger.info("\t\tm.getView().getAccessPublic() "+m.getView().getAccessPublic());
					logger.info("\t\tidentity.isLoggedIn() "+identity.isLoggedIn());
					logger.info("\t\tidentity.hasView(m.getView()) "+identity.hasView(m.getView()));
				}
				
				boolean visible = m.getView().isVisible() && (m.getView().getAccessPublic() || (identity.isLoggedIn() && (m.getView().getAccessLogin() || identity.hasView(m.getView()))));
				if(debugOnInfo) {logger.info("\t\t"+m.getView().getCode()+" visible:"+visible);}
				if(visible)
				{
					M parent = null;
					if(m.getParent()!=null) {parent = m.getParent();}
					else {parent=root;}
					
					mapKey.put(m.getView().getCode(), m);
					if(!mapChild.containsKey(parent)) {mapChild.put(parent,new ArrayList<M>());}
					mapChild.get(parent).add(m);
				}
			}
			setupRequired = false;
		}
	}
	
	public Menu build(String code){setup();if(!mapKey.containsKey(code)) {logger.warn("Code "+code+" not defined");return XmlMenuFactory.build();} else {return menu(mapKey.get(code));}}
	public MenuItem sub(String code) {setup();if(!mapKey.containsKey(code)) {logger.warn("Code "+code+" not defined");return XmlMenuItemFactory.build();} else {return sub(mapKey.get(code));}}
	public MenuItem subDyn(String code, Boolean dyn) {setup();if(!mapKey.containsKey(code)) {logger.warn("Code "+code+" not defined");return XmlMenuItemFactory.build();} else {return sub(mapKey.get(code));}}
	public Breadcrumb breadcrumbDyn(String code, Boolean dyn){setup();if(!mapKey.containsKey(code)) {logger.warn("Code "+code+" not defined");return XmlBreadcrumbFactory.build();} else {return breadcrumb(mapKey.get(code));}}
	public Breadcrumb breadcrumb(String code){setup();if(!mapKey.containsKey(code)) {logger.warn("Code "+code+" not defined");return XmlBreadcrumbFactory.build();} else {return breadcrumb(mapKey.get(code));}}
	
	private Menu menu(M m)
	{
		if(!mapMenu.containsKey(m))
		{
			Menu menu = XmlMenuFactory.build();
			M root = mapKey.get(JeeslSecurityMenu.keyRoot);
			if(mapChild.containsKey(root))
			{
				for(M mi : mapChild.get(root))
				{
					MenuItem xml = xfMenuItem.build(mi);
					xml.setActive(isParent(mi,m));
					if(xml.isActive() && mapChild.containsKey(mi)) {xml.getMenuItem().addAll(childs(mi));}
					menu.getMenuItem().add(xml);
				}
			}	
			if(debugOnInfo){logger.info("Menu for: "+tfMenu.code(m));JaxbUtil.info(menu);}
			mapMenu.put(m,menu);
		}
		return mapMenu.get(m);
	}
	
	private List<MenuItem> childs(M m)
	{
		List<MenuItem> list = new ArrayList<MenuItem>();
		for(M mi : mapChild.get(m))
		{
			MenuItem xml = xfMenuItem.build(mi);
			xml.setActive(isParent(mi,m));
			if(xml.isActive() && mapChild.containsKey(mi)) {xml.getMenuItem().addAll(childs(mi));}
			list.add(xml);
		}
		return list;
	}
	
	private MenuItem sub(M m)
	{
		if(!mapSub.containsKey(m))
		{
			MenuItem mi = XmlMenuItemFactory.build();
			if(mapChild.containsKey(m))
			{
				for(M child : mapChild.get(m))
				{
					mi.getMenuItem().add(xfMenuItem.build(child));
				}
			}
			if(debugOnInfo) {logger.info("Sub for: "+tfMenu.code(m)+" childs:"+mi.getMenuItem().size());JaxbUtil.info(mi);}
			mapSub.put(m,mi);
		}
		return mapSub.get(m);
	}
	
	private Breadcrumb breadcrumb(M m)
	{
		if(!mapBreadcrumb.containsKey(m))
		{
			Breadcrumb xml= XmlBreadcrumbFactory.build();
			breadcrumb(xml,m);
//			Collections.reverse(xml.getMenuItem());
			mapBreadcrumb.put(m,xml);
			if(debugOnInfo) {logger.info("breadcrumb for: "+m.getView().getCode());JaxbUtil.info(xml);}
		}
		return mapBreadcrumb.get(m);
	}
	
	private void breadcrumb(Breadcrumb xml, M m)
	{
		MenuItem mi = xfMenuItem.build(m);
		if(mapChild.containsKey(m)) {mi.getMenuItem().addAll(sub(m).getMenuItem());}
		if(m.getParent()!=null) {breadcrumb(xml,m.getParent());}
		xml.getMenuItem().add(mi);
	}
	
	private boolean isParent(M parent, M child)
	{
		if(child.getParent()==null) {return false;}
		else if(child.getParent().equals(parent)){return true;}
		else {return isParent(parent,child.getParent());}		
	}
}