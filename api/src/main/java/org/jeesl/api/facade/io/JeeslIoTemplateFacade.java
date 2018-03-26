package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoTemplateFacade <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends UtilsStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
			extends UtilsFacade
{	
	TEMPLATE load(TEMPLATE template);
	
	DEFINITION fDefinition(TYPE type, String code) throws UtilsNotFoundException;
	
	<E extends Enum<E>> List<TEMPLATE> loadTemplates(E category);
	List<TEMPLATE> fTemplates(List<CATEGORY> categories, boolean showInvisibleEntities);
	List<TEMPLATE> fTemplates(CATEGORY category, SCOPE scope);
}