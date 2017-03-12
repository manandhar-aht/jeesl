package org.jeesl.factory.ejb.survey;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Question;

import org.jeesl.interfaces.model.module.survey.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyOptionFactory<L extends UtilsLang,
										D extends UtilsDescription,
										SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										SS extends UtilsStatus<SS,L,D>,
										TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyOptionFactory.class);
	
	final Class<OPTION> cOption;
    
	public EjbSurveyOptionFactory(final Class<OPTION> cOption)
	{       
        this.cOption = cOption;
	}
	    
	public OPTION build(QUESTION question, String code)
	{
		OPTION ejb = null;
		try
		{
			ejb = cOption.newInstance();
			ejb.setQuestion(question);
			ejb.setCode(code);
			ejb.setPosition(1);

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}