package org.jeesl.factory.ejb.system.io.template;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoTemplateTokenFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
								DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
								TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateTokenFactory.class);
	
	final Class<TOKEN> cToken;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
    
	protected EjbIoTemplateTokenFactory(final Class<L> cL,final Class<D> cD,final Class<TOKEN> cToken)
	{       
        this.cToken = cToken;
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
	}
		
	public TOKEN build(TEMPLATE template, Entity xml)
	{
		TOKEN ejb = build(template);
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
    
	public TOKEN build(TEMPLATE template)
	{
		TOKEN ejb = null;
		try
		{
			ejb = cToken.newInstance();
			ejb.setTemplate(template);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}