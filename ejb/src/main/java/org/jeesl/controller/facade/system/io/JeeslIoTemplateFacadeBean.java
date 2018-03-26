package org.jeesl.controller.facade.system.io;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoTemplateFacade;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoTemplateFacadeBean<L extends UtilsLang,D extends UtilsDescription,
						CATEGORY extends UtilsStatus<CATEGORY,L,D>,
						TYPE extends UtilsStatus<TYPE,L,D>,
						TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
						SCOPE extends UtilsStatus<SCOPE,L,D>,
						DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
						TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
					extends UtilsFacadeBean
					implements JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoTemplateFacadeBean.class);
	
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
	
	@Override
	public <E extends Enum<E>> List<TEMPLATE> loadTemplates(E category)
	{
		List<TEMPLATE> result = new ArrayList<TEMPLATE>();
		List<CATEGORY> categories = new ArrayList<CATEGORY>();
		
		try {categories.add(this.fByCode(cCategory, category));}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		if(categories.isEmpty()){return result;}
		
		for(TEMPLATE t : fTemplates(categories,false))
		{
			t.getDefinitions().size();
			result.add(t);
		}
		
		return result;
	}
	
	@Override public List<TEMPLATE> fTemplates(List<CATEGORY> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<CATEGORY>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cTemplate,ppCategory);
	}
	
	@Override public List<TEMPLATE> fTemplates(CATEGORY category, SCOPE scope)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TEMPLATE> cQ = cB.createQuery(cTemplate);
		Root<TEMPLATE> template = cQ.from(cTemplate);
		
		Path<CATEGORY> pCategory = template.get(JeeslIoTemplate.Attributes.category.toString());
		Path<SCOPE> pScope = template.get(JeeslIoTemplate.Attributes.scope.toString());
		Path<Boolean> pVisible = template.get(JeeslIoTemplate.Attributes.visible.toString());	

		cQ.where(cB.and(cB.equal(pCategory,category),cB.equal(pScope,scope),cB.equal(pVisible,true)));
		cQ.select(template);
		
		TypedQuery<TEMPLATE> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}

	@Override public DEFINITION fDefinition(TYPE type, String code) throws UtilsNotFoundException
	{
		TEMPLATE t = this.fByCode(cTemplate, code);
		for(DEFINITION d : t.getDefinitions())
		{
			if(d.getType().equals(type)){return d;}
		}
		
		throw new UtilsNotFoundException("No Definition for "+code+" and type="+type.getCode());
	}
}