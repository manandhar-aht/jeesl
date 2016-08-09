package org.jeesl.controller.facade;

import javax.persistence.EntityManager;

import org.jeesl.interfaces.facade.JeeslIoTemplateFacade;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoTemplateFacadeBean<L extends UtilsLang,D extends UtilsDescription,
						CATEGORY extends UtilsStatus<CATEGORY,L,D>,
						TYPE extends UtilsStatus<TYPE,L,D>,
						TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
						DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
						TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
					extends UtilsFacadeBean
					implements JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>
{	
	public JeeslIoTemplateFacadeBean(EntityManager em)
	{
		super(em);
	}
}