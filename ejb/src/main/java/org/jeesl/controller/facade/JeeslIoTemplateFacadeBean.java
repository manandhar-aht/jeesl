package org.jeesl.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.interfaces.facade.JeeslIoTemplateFacade;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
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
	private final Class<CATEGORY> cCategory;
	private final Class<TEMPLATE> cTemplate;
	
	public JeeslIoTemplateFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<TEMPLATE> cTemplate)
	{
		super(em);
		this.cCategory=cCategory;
		this.cTemplate=cTemplate;
	}
	
	@Override public TEMPLATE load(TEMPLATE template)
	{
		template = em.find(cTemplate, template.getId());
		template.getTokens().size();
		template.getDefinitions().size();
		return template;
	}
	
	@Override public List<TEMPLATE> fTemplates(List<CATEGORY> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<CATEGORY>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cTemplate,ppCategory);
	}

	@Override
	public DEFINITION fDefinition(TYPE type, String code) throws UtilsNotFoundException
	{
		TEMPLATE t = this.fByCode(cTemplate, code);
		for(DEFINITION d : t.getDefinitions())
		{
			if(d.getType().equals(type)){return d;}
		}
		
		throw new UtilsNotFoundException("No Definition for "+code+" and type="+type.getCode());
	}
}