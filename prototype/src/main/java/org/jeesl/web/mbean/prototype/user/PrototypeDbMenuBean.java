package org.jeesl.web.mbean.prototype.user;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jeesl.factory.xml.system.navigation.XmlMenuItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.navigation.Breadcrumb;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.exlp.util.xml.JaxbUtil;

public class PrototypeDbMenuBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(PrototypeDbMenuBean.class);
	private static final long serialVersionUID = 1L;
	
	protected static final String rootMain = "root";
	protected Map<String,Menu> mapMenu;
	protected Map<String,MenuItem> mapSub;
	protected Map<String,Breadcrumb> mapBreadcrumb;
	
	protected Map<String,Boolean> mapViewAllowed;
	protected boolean userLoggedIn;
	
	protected Menu menu;
	protected MenuFactory mfMain;

	public PrototypeDbMenuBean()
	{
		userLoggedIn = false;	
	}
	
	@PostConstruct
    public void init()
    {
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		
		try
		{
			this.initMaps();
			Access xmlAccess = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/views.xml", Access.class);
			Menu xmlMenuMain = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/menu.xml", Menu.class);
			
			if(logger.isTraceEnabled()){logger.info("main.root="+rootMain);}

			mfMain = new MenuFactory(xmlMenuMain,xmlAccess,getLang(),rootMain);
			mfMain.setAlwaysUpToLevel(1);
		}
		catch (FileNotFoundException e)
		{
			throw new IllegalStateException("Class: " +e.getClass().getName() +" Message: " +e.getMessage());
		}
		logger.info(AbstractLogMessage.postConstruct(ptt));
    }
	
    public void initMaps() throws FileNotFoundException
    {
		mapMenu = new Hashtable<String,Menu>();
		mapSub = new Hashtable<String,MenuItem>();
		mapBreadcrumb = new Hashtable<String,Breadcrumb>();
    }
    
	public void clear(){clear(false);}
	public void clear(boolean userLoggedIn)
	{
		logger.trace("Clearing hashtables ... userLoggedIn:"+userLoggedIn);
		this.userLoggedIn=userLoggedIn;
		mapMenu.clear();
		mapSub.clear();
		mapBreadcrumb.clear();
		mapViewAllowed = null;
	}
	
	protected void buildViewAllowedMap()
	{
		logger.warn("This should never been called here. A @Override in extended class is required");
	}

	// ******************************************
	// Menu
	protected Menu menu(MenuFactory mf, String code) {return menu(mf,code,userLoggedIn);}
	protected Menu menu(MenuFactory mf, String code, boolean loggedIn)
	{
		buildViewAllowedMap();
		if(code==null || code.length()==0){code=rootMain;}
		if(!mapMenu.containsKey(code))
		{
			ProcessingTimeTracker ptt = null;
			if(logger.isTraceEnabled()){ptt = new ProcessingTimeTracker(true);}
			synchronized(mf)
			{
				mapMenu.put(code, mf.build(mapViewAllowed,code,loggedIn));
			}
			if(logger.isTraceEnabled()){logger.trace(AbstractLogMessage.time("Menu creation for "+code,ptt));}
		}
		return mapMenu.get(code);
	}
	
	/**
	 * Breadcrumb
	 */
	public Breadcrumb breadcrumb(MenuFactory mf,String code){return breadcrumb(mf,false,code,false,false);}
	public Breadcrumb breadcrumb(MenuFactory mf,boolean withRoot, String code, boolean withFirst, boolean withChilds){return breadcrumb(mf,withRoot,code,withFirst,withChilds,null);}
	public Breadcrumb breadcrumb(MenuFactory mf,boolean withRoot, String code, boolean withFirst, boolean withChilds, Menu dynamicMenu)
	{
		if(!mapBreadcrumb.containsKey(code))
		{
			ProcessingTimeTracker ptt = null;
			if(logger.isTraceEnabled()){ptt = new ProcessingTimeTracker(true);}
			synchronized(mf)
			{
				boolean mapMenuContainsCode = mapMenu.containsKey(code);
//				logger.info("breadcrumb contains "+code+"?"+mapMenuContainsCode);
				if(!mapMenuContainsCode)
				{
//					logger.info("Building Menu");
					if(dynamicMenu!=null){mf.addDynamicNodes(dynamicMenu);}
					menu(mf,code);
				}
				Breadcrumb bOrig = mf.breadcrumb(withRoot,code);
				Breadcrumb bClone = new Breadcrumb();
				int startIndex=0;
				if(bOrig.getMenuItem().size()>1 && !withFirst){startIndex=1;}
				for(int i=startIndex;i<bOrig.getMenuItem().size();i++)
				{
					bClone.getMenuItem().add(XmlMenuItemFactory.clone(bOrig.getMenuItem().get(i)));
				}
				JaxbUtil.trace(bClone);
				//Issue Utils-228
		/*		if(b.getMenuItem().size()>1 && !withFirst)
				{
					b.getMenuItem().remove(0);
				}
				*/
				if(withChilds)
				{
					for(MenuItem mi : bClone.getMenuItem())
					{
						for(MenuItem subOrig : sub(mf, mi.getCode()).getMenuItem())
						{
							mi.getMenuItem().add(XmlMenuItemFactory.clone(subOrig));
						}
					}
				}
				mapBreadcrumb.put(code,bClone);
				if(logger.isTraceEnabled())
				{
					JaxbUtil.info(mapBreadcrumb.get(code));
				}
			}
			if(logger.isTraceEnabled()){logger.trace(AbstractLogMessage.time("Breadcrumb creation for "+code,ptt));}
		}
		return mapBreadcrumb.get(code);
	}
	
	// ******************************************
	// SubMenu
	public MenuItem sub(MenuFactory mf, String code){return subDyn(mf,code,null);}
	public MenuItem subDyn(MenuFactory mf, String code, Menu dynamicMenu)
	{
		boolean mapSubContaines = mapSub.containsKey(code);
//		logger.info("Creating sub... dynamic?"+(dynamicMenu!=null)+" mapContains:"+mapSubContaines);
		
		if(!mapSubContaines)
		{
			ProcessingTimeTracker ptt=null;
			if(logger.isTraceEnabled()){ptt = new ProcessingTimeTracker(true);}
			synchronized(mf)
			{
				if(!mapMenu.containsKey(code))
				{
					if(dynamicMenu!=null){mf.addDynamicNodes(dynamicMenu);}
					menu(mf,code);
				}
				Menu m = mapMenu.get(code);
				mapSub.put(code,mf.subMenu(m,code));
			}
//			JaxbUtil.trace(mapSub.get(code));
			if(logger.isTraceEnabled()){logger.trace(AbstractLogMessage.time("Submenu creation for "+code,ptt));}
		}
		return mapSub.get(code);
	}
	
	public void userLoggedIn(Map<String, Boolean> allowedViews)
	{
		this.clear(true);
		mapViewAllowed = allowedViews;
	}
	
	public void clearAndRemoveChilds(String key, String dynKey)
	{
		mfMain.removeChilds(key,dynKey);
		mapMenu.remove(key);
		mapSub.remove(key);
		mapBreadcrumb.remove(key);
	}
	
	protected String getLang(){return "en";}
	
	public Menu build() {return this.menu(mfMain, rootMain);}
	public Menu build(String code){return this.menu(mfMain, code);}
	
	public Breadcrumb breadcrumb(String code){return this.breadcrumb(mfMain, false, code, true, true);}
	public Breadcrumb breadcrumbDyn(String code,Menu dynamic){return this.breadcrumb(mfMain, false, code, true, true, dynamic);}
	
	public MenuItem sub(String code){return this.sub(mfMain, code);}
	public MenuItem subDyn(String code,Menu dynamic){return this.subDyn(mfMain,code,dynamic);}
}