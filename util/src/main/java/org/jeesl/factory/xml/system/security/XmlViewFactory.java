package org.jeesl.factory.xml.system.security;

import org.jeesl.factory.xml.system.navigation.XmlNavigationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.security.View;

public class XmlViewFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
		
	private View q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescription;
	private XmlNavigationFactory<L,D,C,R,V,U,A,AT,USER> xfNavigation;
	
	public XmlViewFactory(View q)
	{
		this.q=q;
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()) {xfDescription = new XmlDescriptionsFactory<D>(q.getDescriptions());}
		if(q.isSetNavigation()) {xfNavigation = new XmlNavigationFactory<L,D,C,R,V,U,A,AT,USER>(q.getNavigation());}
	}
	

	public View build(V view)
	{
		View xml = new View();
		if(q.isSetCode()){xml.setCode(view.getCode());}
		if(q.isSetPosition()){xml.setPosition(view.getPosition());}
		if(q.isSetVisible()){xml.setVisible(view.isVisible());}
		if(q.isSetDocumentation() && view.getDocumentation()!=null){xml.setDocumentation(view.getDocumentation());}
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(view.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescription.create(view.getDescription()));}
		if(q.isSetNavigation()){xml.setNavigation(xfNavigation.build(view));}
		
		return xml;
	}
	
	public static View build(String code)
	{
		View xml = new View();
		xml.setCode(code);
		return xml;
	}
}