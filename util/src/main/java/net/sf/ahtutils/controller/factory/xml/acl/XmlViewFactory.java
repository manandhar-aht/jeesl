package net.sf.ahtutils.controller.factory.xml.acl;

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
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.access.View;

public class XmlViewFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
		
	private View q;
	@SuppressWarnings("unused")
	private String lang;
	
	public XmlViewFactory(View q, String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclView<L,D,CU,U>>
		View getUsecase(U usecase)
	{
		View xml = new View();
//		if(q.isSetIndex()){xml.setIndex(usecase.g);
		if(q.isSetCode()){xml.setCode(usecase.getCode());}
//		if(q.isSetDocumentation()){xml.setDocumentation(usecase.getDocumentation());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(usecase.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(usecase.getDescription()));
		}
		
		return xml;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,STAFF extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,DOMAIN>,DOMAIN extends EjbWithId>
		View build(V view)
	{
		View xml = new View();
		
		if(q.isSetCode()){xml.setCode(view.getCode());}
		if(q.isSetPosition()){xml.setPosition(view.getPosition());}
		if(q.isSetVisible()){xml.setVisible(view.isVisible());}
		if(q.isSetDocumentation() && view.getDocumentation()!=null){xml.setDocumentation(view.getDocumentation());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(view.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(view.getDescription()));
		}
		
		if(q.isSetPublic() && view.getAccessPublic()!=null){xml.setPublic(view.getAccessPublic());}
		if(q.isSetOnlyLoginRequired() && view.getAccessLogin()!=null){xml.setOnlyLoginRequired(view.getAccessLogin());}
		
		return xml;
	}
	
	public static View create(String code)
	{
		View xml = new View();
		xml.setCode(code);
		return xml;
	}
}