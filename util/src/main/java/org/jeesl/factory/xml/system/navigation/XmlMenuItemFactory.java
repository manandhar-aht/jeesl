package org.jeesl.factory.xml.system.navigation;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.system.navigation.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.access.View;

public class XmlMenuItemFactory <L extends UtilsLang, D extends UtilsDescription,
								C extends JeeslSecurityCategory<L,D>,
								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
								V extends JeeslSecurityView<L,D,C,R,U,A>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								M extends JeeslSecurityMenu<V,M>,
								USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlMenuItemFactory.class);
	
	private final String localeCode;
	
	public XmlMenuItemFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public MenuItem build(M menu)
	{
		MenuItem xml = new MenuItem();
		xml.setCode(menu.getView().getCode());
		xml.setName(menu.getView().getName().get(localeCode).getLang());
		xml.setHref(menu.getView().getUrlMapping());
		xml.setActive(false);
		return xml;
	}
	
	public static MenuItem clone(MenuItem item)
	{
		MenuItem xml = new MenuItem();
		xml.setName(item.getName());
		xml.setHref(item.getHref());
		xml.setCode(item.getCode());

		return xml;
	}
	
	public static MenuItem build(MenuItem mi)
	{
		MenuItem xml = new MenuItem();
		xml.setActive(mi.isSetActive() && mi.isActive());
		xml.setCode(mi.getCode());
		xml.setHref(mi.getHref());
		xml.setName(mi.getName());
		
		return xml;
	}
	
	public static MenuItem create(String label)
	{
		MenuItem xml = new MenuItem();
		xml.setName(label);
		return xml;
	}
	
	public static MenuItem dynamic(String dynamicCode, String urlParameter, String label)
	{
		View view = new View();
		view.setCode(dynamicCode);
		view.setUrlParameter(urlParameter);
		view.setLabel(label);

		MenuItem item = new MenuItem();
		item.setCode(dynamicCode+view.getUrlParameter());
		item.setView(view);
		return item;
	}
	
	public static MenuItem buildItem(String label, String href)
	{
		MenuItem mi = new MenuItem();
		mi.setName(label);
		mi.setHref(href);
		return mi;
	}
	
	public static MenuItem build()
	{
		return new MenuItem();
	}
}