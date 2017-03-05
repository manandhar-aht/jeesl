package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoTemplateFacade <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends UtilsStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
										TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>>
			extends UtilsFacade
{	
	TEMPLATE load(TEMPLATE template);
	
	DEFINITION fDefinition(TYPE type, String code) throws UtilsNotFoundException;
	
	<E extends Enum<E>> List<TEMPLATE> loadTemplates(E category);
	List<TEMPLATE> fTemplates(List<CATEGORY> categories, boolean showInvisibleEntities);
	List<TEMPLATE> fTemplates(CATEGORY category, SCOPE scope);
}