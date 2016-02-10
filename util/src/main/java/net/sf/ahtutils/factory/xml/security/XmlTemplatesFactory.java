package net.sf.ahtutils.factory.xml.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.security.Templates;

public class XmlTemplatesFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplatesFactory.class);
		
	private Templates q;
	
	public XmlTemplatesFactory(Templates q)
	{
		this.q=q;
	}
	

	public Templates build(List<AT> templates)
	{
		XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER>(q.getTemplate().get(0));
		
		Templates xml = build();
		for(AT template : templates)
		{
			xml.getTemplate().add(f.build(template));
		}
		return xml;
	}
	
	public static Templates build()
	{
		return new Templates();
	}

}