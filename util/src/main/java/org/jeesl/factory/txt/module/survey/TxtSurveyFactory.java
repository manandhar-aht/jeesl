package org.jeesl.factory.txt.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class TxtSurveyFactory <L extends UtilsLang, D extends UtilsDescription,
										SURVEY extends JeeslSurvey<L,D,?,TEMPLATE,?>,
										
										TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>
										>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSurveyFactory.class);
		
	private final String localeCode;
	
	public TxtSurveyFactory(final String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public String debug(SURVEY survey)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(survey.getName().get(localeCode).getLang());
		sb.append(" (").append(survey.getTemplate().getName()).append(")");
		return sb.toString();
	}
}