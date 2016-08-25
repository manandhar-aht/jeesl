package org.jeesl.factory.xml.system.security;

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
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.security.Category;

public class XmlCategoryFactory <L extends UtilsLang,D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	private String lang;
	private net.sf.ahtutils.xml.security.Category q;
	
	public XmlCategoryFactory(Query q){this(q.getLang(),q.getCategory());}
	public XmlCategoryFactory(String lang,net.sf.ahtutils.xml.security.Category q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public net.sf.ahtutils.xml.security.Category build(C category)
	{
		Category xml = new Category();
		if(q.isSetCode()){xml.setCode(category.getCode());}
		if(q.isSetPosition()){xml.setPosition(category.getPosition());}
		if(q.isSetVisible()){xml.setVisible(category.isVisible());}
		if(q.isSetDocumentation() && category.getDocumentation()!=null){xml.setDocumentation(category.getDocumentation());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(category.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(category.getDescription()));
		}
		
		if(q.isSetLabel() && lang!=null)
		{
			if(category.getName()!=null)
			{
				if(category.getName().containsKey(lang)){xml.setLabel(category.getName().get(lang).getLang());}
				else
				{
					String msg = "No translation "+lang+" available in "+category;
					logger.warn(msg);
					xml.setLabel(msg);
				}
			}
			else
			{
				String msg = "No @name available in "+category;
				logger.warn(msg);
				xml.setLabel(msg);
			}
		}
		
		return xml;
	}
}