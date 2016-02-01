package net.sf.ahtutils.factory.xml.security;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
		
	private View q;
	
	public XmlViewFactory(View q)
	{
		this.q=q;
	}
	

	public View build(V view)
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
		return xml;
	}
	
	public static View build(String code)
	{
		View xml = new View();
		xml.setCode(code);
		return xml;
	}
}