package net.sf.ahtutils.factory.xml.security;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.security.Usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUsecaseFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlUsecaseFactory.class);
		
	private Usecase q;
	
	public XmlUsecaseFactory(Usecase q)
	{
		this.q=q;
	}
	

	public Usecase build(U usecase)
	{
		Usecase xml = new Usecase();
		if(q.isSetCode()){xml.setCode(usecase.getCode());}
		if(q.isSetPosition()){xml.setPosition(usecase.getPosition());}
		if(q.isSetVisible()){xml.setVisible(usecase.isVisible());}
		if(q.isSetDocumentation() && usecase.getDocumentation()!=null){xml.setDocumentation(usecase.getDocumentation());}
		
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
		
		if(q.isSetViews())
		{
			XmlViewsFactory<L,D,C,R,V,U,A,USER> f = new XmlViewsFactory<L,D,C,R,V,U,A,USER>(q.getViews());
			xml.setViews(f.build(usecase.getViews()));
		}
		
		if(q.isSetActions())
		{
			XmlActionsFactory<L,D,C,R,V,U,A,USER> f = new XmlActionsFactory<L,D,C,R,V,U,A,USER>(q.getActions());
			xml.setActions(f.build(usecase.getActions()));
		}
		
		return xml;
	}
	
	public static Usecase build(String code)
	{
		Usecase xml = new Usecase();
		xml.setCode(code);
		return xml;
	}
}