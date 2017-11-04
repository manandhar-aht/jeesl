package net.sf.ahtutils.controller.factory.xml.security;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.security.XmlActionsFactory;
import org.jeesl.factory.xml.system.security.XmlUsecasesFactory;
import org.jeesl.factory.xml.system.security.XmlViewsFactory;
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
import net.sf.ahtutils.xml.access.Role;
import net.sf.exlp.util.io.StringUtil;

@Deprecated
public class XmlRoleFactory <L extends UtilsLang,
	D extends UtilsDescription,
	C extends JeeslSecurityCategory<L,D>,
	R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	V extends JeeslSecurityView<L,D,C,R,U,A>,
	U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
	A extends JeeslSecurityAction<L,D,R,V,U,AT>,
	AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
	USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	private Role q;
	private net.sf.ahtutils.xml.security.Role qSec;
	private String lang;
	
	public XmlRoleFactory(Role q){this(q,null);}
	public XmlRoleFactory(Role q,String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
	public XmlRoleFactory(net.sf.ahtutils.xml.security.Role qSec){this(qSec,null);}
	public XmlRoleFactory(net.sf.ahtutils.xml.security.Role qSec,String lang)
	{
		this.qSec=qSec;
		this.lang=lang;
	}
	
    public Role create(R ejb)
    {
    	Role xml = new Role();
    	if(q.isSetCode()){xml.setCode(ejb.getCode());}
    	if(q.isSetName() && ejb.getName()!=null && ejb.getName().containsKey(lang))
    	{
    		xml.setName(ejb.getName().get(lang).getLang());
    	}
    	
    	if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
    	
    	return xml;
    }
    
    public net.sf.ahtutils.xml.security.Role build(R role)
    {
    	if(logger.isTraceEnabled())
    	{
    		logger.info(StringUtil.stars());
    		logger.info(role.toString());
    		logger.info("Query: "+q.isSetDocumentation());
    		logger.info("\t"+(role.getDocumentation()!=null));
    		if(role.getDocumentation()!=null){logger.info("\t"+role.getDocumentation());}
    	}
    	
    	net.sf.ahtutils.xml.security.Role xml = new net.sf.ahtutils.xml.security.Role();
    	if(q.isSetCode()){xml.setCode(role.getCode());}
//		if(q.isSetPosition()){xml.setPosition(role.getPosition());}
//		if(q.isSetVisible()){xml.setVisible(role.isVisible());}
		if(q.isSetDocumentation() && role.getDocumentation()!=null){xml.setDocumentation(role.getDocumentation());}
    	
    	if(qSec.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qSec.getLangs());
			xml.setLangs(f.getUtilsLangs(role.getName()));
		}
    	
		if(qSec.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qSec.getDescriptions());
			xml.setDescriptions(f.create(role.getDescription()));
		}
		
		if(qSec.isSetViews())
		{
			XmlViewsFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlViewsFactory<L,D,C,R,V,U,A,AT,USER>(qSec.getViews());
			xml.setViews(f.build(role.getViews()));
		}
		
		if(qSec.isSetActions())
		{
			XmlActionsFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlActionsFactory<L,D,C,R,V,U,A,AT,USER>(qSec.getActions());
			xml.setActions(f.build(role.getActions()));
		}
		
		if(qSec.isSetUsecases())
		{
			XmlUsecasesFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlUsecasesFactory<L,D,C,R,V,U,A,AT,USER>(qSec.getUsecases());
			xml.setUsecases(f.build(role.getUsecases()));
		}
    	
    	return xml;
    }
    
    public static net.sf.ahtutils.xml.security.Role id()
    {
    	net.sf.ahtutils.xml.security.Role role = new net.sf.ahtutils.xml.security.Role();
    	role.setId(0);
    	return role;
    }
    
    public static net.sf.ahtutils.xml.security.Role build(String code)
    {
    	net.sf.ahtutils.xml.security.Role role = new net.sf.ahtutils.xml.security.Role();
    	role.setCode(code);
    	return role;
    }
}