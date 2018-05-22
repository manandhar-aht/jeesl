package org.jeesl.factory.txt.system.io.mail.template;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtIoTemplateTokenFactory <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends UtilsStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
										TOKENTYPE extends UtilsStatus<TOKENTYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoTemplateTokenFactory.class);
		
	public Map<String,Object> buildModel(TEMPLATE template)
	{
		Map<String,Object> model = new HashMap<String,Object>();
		for(TOKEN token : template.getTokens())
		{
			model.put(token.getCode(), token.getExample());
		}
		return model;
	}
}