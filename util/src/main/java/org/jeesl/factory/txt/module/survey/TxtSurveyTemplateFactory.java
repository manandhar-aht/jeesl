package org.jeesl.factory.txt.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtSurveyTemplateFactory <TEMPLATE extends JeeslSurveyTemplate<?,?,?,TEMPLATE,?,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSurveyTemplateFactory.class);
		
	private final String localeCode;
	
	public TxtSurveyTemplateFactory(final String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public String debug(TEMPLATE template)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(template.getName());
		sb.append(" ").append(template.getRemark());
		sb.append(" ").append(template.getCategory().getName().get(localeCode).getLang());
		return sb.toString();
	}
}