package org.jeesl.factory.txt.system.io.mail.template;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtIoTemplateTokenFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoTemplateTokenFactory.class);
		
	public static <L extends UtilsLang,D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>, TYPE extends UtilsStatus<TYPE,L,D>, TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>, SCOPE extends UtilsStatus<SCOPE,L,D>,DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>, TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
		Map<String,String> buildModel(TEMPLATE template)
	{
		Map<String,String> model = new HashMap<String,String>();
		for(TOKEN token : template.getTokens())
		{
			model.put(token.getCode(), token.getExample());
		}
		return model;
	}
}