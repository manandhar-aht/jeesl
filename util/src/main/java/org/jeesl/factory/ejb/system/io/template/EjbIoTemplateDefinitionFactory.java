package org.jeesl.factory.ejb.system.io.template;

import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
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

public class EjbIoTemplateDefinitionFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateDefinitionFactory.class);
	
	final Class<DEFINITION> cDefinition;
	
	private EjbDescriptionFactory<D> efDescription;
    
	protected EjbIoTemplateDefinitionFactory(final Class<D> cD,final Class<DEFINITION> cDefinition)
	{       
        this.cDefinition = cDefinition;
		efDescription = EjbDescriptionFactory.factory(cD);
	}
	
	public DEFINITION build(TEMPLATE template, TYPE type, Entity xml)
	{
		DEFINITION ejb = build(template,type);
		try
		{
			ejb.setDescription(efDescription.create(xml.getDescriptions()));
		}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		return ejb;
	}
    
	public DEFINITION build(TEMPLATE template, TYPE type)
	{
		DEFINITION ejb = null;
		try
		{
			ejb = cDefinition.newInstance();
			ejb.setTemplate(template);
			ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}