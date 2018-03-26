package org.jeesl.factory.ejb.system.io.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoTemplateFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateFactory.class);
	
	final Class<TEMPLATE> cTemplate;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
    
	protected EjbIoTemplateFactory(final Class<L> cL,final Class<D> cD,final Class<TEMPLATE> cTemplate)
	{       
        this.cTemplate = cTemplate;
		efLang = EjbLangFactory.factory(cL);
		efDescription = EjbDescriptionFactory.factory(cD);
	}
	
	public TEMPLATE build(CATEGORY category, Entity xml)
	{
		TEMPLATE ejb = build(category);
		ejb.setCode(xml.getCode());
		ejb.setPosition(xml.getPosition());
		try
		{
			ejb.setName(efLang.getLangMap(xml.getLangs()));
			ejb.setDescription(efDescription.create(xml.getDescriptions()));
		}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		return ejb;
	}
    
	public TEMPLATE build(CATEGORY category)
	{
		TEMPLATE ejb = null;
		try
		{
			ejb = cTemplate.newInstance();
			ejb.setCategory(category);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
					SCOPE extends UtilsStatus<SCOPE,L,D>,
					DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
					TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
		Map<String,TEMPLATE> buildMap(List<TEMPLATE> templates)
	{
		Map<String,TEMPLATE> map = new HashMap<String,TEMPLATE>();
		for(TEMPLATE t : templates){map.put(t.getCode(),t);}
		return map;
	}
}