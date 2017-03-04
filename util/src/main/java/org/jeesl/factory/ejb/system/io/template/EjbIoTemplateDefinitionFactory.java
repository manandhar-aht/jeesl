package org.jeesl.factory.ejb.system.io.template;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoTemplateDefinitionFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateDefinitionFactory.class);
	
	final Class<DEFINITION> cDefinition;
	
	private EjbDescriptionFactory<D> efDescription;
    
	protected EjbIoTemplateDefinitionFactory(final Class<D> cD,final Class<DEFINITION> cDefinition)
	{       
        this.cDefinition = cDefinition;
		efDescription = EjbDescriptionFactory.createFactory(cD);
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