package org.jeesl.controller.report.system;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.xml.system.navigation.XmlMenuFactory;
import org.jeesl.factory.xml.system.navigation.XmlMenuItemFactory;
import org.jeesl.factory.xml.system.security.XmlSecurityFactory;
import org.jeesl.factory.xml.system.security.XmlViewFactory;
import org.jeesl.factory.xml.system.util.text.XmlDescriptionFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.system.navigation.Menu;
import org.jeesl.model.xml.system.navigation.MenuItem;
import org.jeesl.util.comparator.ejb.system.security.SecurityViewComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Security;

public class JeeslMenuStructureReport <L extends UtilsLang,
								D extends UtilsDescription,
								C extends JeeslSecurityCategory<L,D>,
								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
								V extends JeeslSecurityView<L,D,C,R,U,A>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
								AT extends JeeslSecurityTemplate<L,D,C>,
								M extends JeeslSecurityMenu<V,M>,
								USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslMenuStructureReport.class);

	private final JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity;
	private final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,?,USER> fbSecurity;
	
	private final String localeCode;
	
//	private org.jeesl.factory.xml.system.security.XmlViewFactory<L,D,C,R,V,U,A,AT,USER> xfView;
	private Comparator<V> comparatorView;
	
	public JeeslMenuStructureReport(String localeCode, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity, SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,?,USER> fbSecurity)
	{
		this.localeCode=localeCode;
		this.fSecurity=fSecurity;
		this.fbSecurity=fbSecurity;
		
		comparatorView = (new SecurityViewComparator<V>()).factory(SecurityViewComparator.Type.position);
	}
	
	public Security build()
	{
		Menu menu = XmlMenuFactory.build();
		List<V> views;
		views = fSecurity.all(fbSecurity.getClassView());
		Collections.sort(views,comparatorView);
		for(V view : views)
		{
			menu.getMenuItem().add(build(view));
		}
		
		return XmlSecurityFactory.build(menu);
	}
	
	private MenuItem build(V view)
	{
		MenuItem item = XmlMenuItemFactory.build();
		
		item.setView(XmlViewFactory.create(view.getCategory().getPosition()+"."+view.getPosition()));
		item.getView().setLabel(view.getCategory().getName().get(localeCode).getLang());
		
		item.setCode(view.getCode());
		item.setName(view.getName().get(localeCode).getLang());
		item.setDescription(XmlDescriptionFactory.build(view.getDescription().get(localeCode).getLang()));
		return item;
	}
}