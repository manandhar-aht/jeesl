package org.jeesl.factory.xml.system.navigation;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.model.xml.system.navigation.Navigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlNavigationFactory <L extends UtilsLang,
									D extends UtilsDescription, 
									C extends JeeslSecurityCategory<L,D>,
									R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
									V extends JeeslSecurityView<L,D,C,R,U,A>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
									A extends JeeslSecurityAction<L,D,R,V,U,AT>,
									AT extends JeeslSecurityTemplate<L,D,C>,
									USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlNavigationFactory.class);
		
	private Navigation q;
	
	public XmlNavigationFactory(Navigation q)
	{
		this.q=q;
	}
	
	public Navigation build(V view)
	{
		Navigation xml = new Navigation();
		if(q.isSetPackage()){xml.setPackage(view.getPackageName());}
		if(q.isSetViewPattern() && view.getViewPattern()!=null){xml.setViewPattern(XmlViewPatternFactory.build(view.getViewPattern()));}
		if(q.isSetUrlMapping() && view.getUrlMapping()!=null){xml.setUrlMapping(XmlUrlMappingFactory.build(view.getUrlMapping(), view.getUrlBase()));}
		
		return xml;
	}
}