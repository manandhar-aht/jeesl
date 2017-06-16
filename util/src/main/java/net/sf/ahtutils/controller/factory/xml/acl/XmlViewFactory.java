package net.sf.ahtutils.controller.factory.xml.acl;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;

public class XmlViewFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
		
	private net.sf.ahtutils.xml.access.View qOld;
	@SuppressWarnings("unused")
	private String localeCode;
	
	public XmlViewFactory(net.sf.ahtutils.xml.access.View q, String localeCode)
	{
		this.qOld=q;
		this.localeCode=localeCode;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclView<L,D,CU,U>>
	net.sf.ahtutils.xml.access.View getUsecase(U usecase)
	{
		net.sf.ahtutils.xml.access.View xml = new net.sf.ahtutils.xml.access.View();
		if(qOld.isSetCode()){xml.setCode(usecase.getCode());}
		
		if(qOld.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qOld.getLangs());
			xml.setLangs(f.getUtilsLangs(usecase.getName()));
		}
		
		if(qOld.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qOld.getDescriptions());
			xml.setDescriptions(f.create(usecase.getDescription()));
		}
		
		return xml;
	}
}