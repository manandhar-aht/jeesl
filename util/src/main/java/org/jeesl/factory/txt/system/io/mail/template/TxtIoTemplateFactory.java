package org.jeesl.factory.txt.system.io.mail.template;

import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateEnvelope;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtIoTemplateFactory<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
									TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoTemplateFactory.class);
		
	public String buildCode(TEMPLATE template, DEFINITION definition, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(template.getCode());
		sb.append("-").append(definition.getType().getCode());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>, TYPE extends UtilsStatus<TYPE,L,D>, TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>, SCOPE extends UtilsStatus<SCOPE,L,D>,DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>, TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,?>>
		String buildCode(DEFINITION definition, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(definition.getTemplate().getCode());
		sb.append("-").append(definition.getType().getCode());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public String buildCode(TEMPLATE template, TYPE type, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(template.getCode());
		sb.append("-").append(type.getCode());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public <TC extends Enum<TC>> String buildCode(TEMPLATE template, TC typeCode, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(template.getCode());
		sb.append("-").append(typeCode.toString());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public static  <TC extends Enum<TC>> String buildCode(String template, TC typeCode, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(template);
		sb.append("-").append(typeCode.toString());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	public static <TE extends Enum<TE>, TC extends Enum<TC>> String buildCode(TE templateCode, TC typeCode, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(templateCode.toString());
		sb.append("-").append(typeCode.toString());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>, TYPE extends UtilsStatus<TYPE,L,D>, TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>, SCOPE extends UtilsStatus<SCOPE,L,D>,DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>, TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,?>>
		String buildCode(JeeslIoTemplateEnvelope<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> envelope)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(envelope.getTemplate().getCode());
		sb.append("-").append(envelope.getType().getCode());
		sb.append("-").append(envelope.getLocaleCode());
		return sb.toString();
	}
}