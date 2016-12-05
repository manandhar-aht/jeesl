package org.jeesl.factory.xml.system.io.template;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.jeesl.model.xml.system.io.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlTemplateFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
								DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
								TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplateFactory.class);
	
	private Template q;
	
	private XmlCategoryFactory xfCategory;
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;

//	public XmlTemplateFactory(Query q){this(q.getTemplate());}
	public XmlTemplateFactory(Template q)
	{
		this.q=q;
		if(q.isSetCategory()){xfCategory = new XmlCategoryFactory(q.getCategory());}
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
	}
	
	public Template build(TEMPLATE ejb)
	{
		Template xml = new Template();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetCategory()){xml.setCategory(xfCategory.build(ejb.getCategory()));}		
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
				
		return xml;
	}
}