package org.jeesl.factory.xml.system.security;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Action;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;

public class XmlActionFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlActionFactory.class);
		
	private net.sf.ahtutils.xml.security.Action q;
	private net.sf.ahtutils.xml.access.Action qAcl;
	
	public XmlActionFactory(net.sf.ahtutils.xml.security.Action q)
	{
		this.q=q;
	}
	public XmlActionFactory(net.sf.ahtutils.xml.access.Action qAcl)
	{
		this.qAcl=qAcl;
	}

	public net.sf.ahtutils.xml.security.Action build(A action)
	{
		Action xml = new Action();
		if(q.isSetCode()){xml.setCode(action.getCode());}
		if(q.isSetPosition()){xml.setPosition(action.getPosition());}
		if(q.isSetVisible()){xml.setVisible(action.isVisible());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(action.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(action.getDescription()));
		}
		
		if(q.isSetView())
		{
			XmlViewFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlViewFactory<L,D,C,R,V,U,A,AT,USER>(q.getView());
			xml.setView(f.build(action.getView()));
		}
		
		if(q.isSetTemplate() && action.getTemplate()!=null)
		{
			XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER>(q.getTemplate());
			xml.setTemplate(f.build(action.getTemplate()));
		}
		
		return xml;
	}
	
	public net.sf.ahtutils.xml.access.Action create(A action)
	{
		net.sf.ahtutils.xml.access.Action xml = new net.sf.ahtutils.xml.access.Action();
		if(qAcl.isSetCode()){xml.setCode(action.getCode());}
		
		boolean processTemplate = action.getTemplate()!=null;
		
		if(qAcl.isSetLangs() && !processTemplate)
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qAcl.getLangs());
			xml.setLangs(f.getUtilsLangs(action.getName()));
		}
		
		if(qAcl.isSetDescriptions() && !processTemplate)
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qAcl.getDescriptions());
			xml.setDescriptions(f.create(action.getDescription()));
		}
		
		if(qAcl.isSetTemplate() && processTemplate)
		{
			
			XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER>(qAcl.getTemplate());
			xml.setTemplate(f.build(action.getTemplate()));
		}
		
		return xml;
	}
	
	public static net.sf.ahtutils.xml.security.Action build(String code)
	{
		net.sf.ahtutils.xml.security.Action xml = new net.sf.ahtutils.xml.security.Action();
		xml.setCode(code);
		return xml;
	}
	
	public static Langs toLangs(net.sf.ahtutils.xml.access.Action action)
	{		
		if(action.getTemplate()==null) {return action.getLangs();}
		else {return action.getTemplate().getLangs();}
	}	
	public static Langs toLangs(net.sf.ahtutils.xml.security.Action action)
	{		
		if(action.getTemplate()==null) {return action.getLangs();}
		else {return action.getTemplate().getLangs();}
	}
	
	public static Descriptions toDescriptions(net.sf.ahtutils.xml.access.Action action)
	{		
		if(action.getTemplate()==null) {return action.getDescriptions();}
		else {return action.getTemplate().getDescriptions();}
	}
	public static Descriptions toDescriptions(net.sf.ahtutils.xml.security.Action action)
	{		
		if(action.getTemplate()==null) {return action.getDescriptions();}
		else {return action.getTemplate().getDescriptions();}
	}
}