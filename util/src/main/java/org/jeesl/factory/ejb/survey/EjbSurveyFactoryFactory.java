package org.jeesl.factory.ejb.survey;

import org.jeesl.interfaces.model.survey.JeeslSurvey;
import org.jeesl.interfaces.model.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbSurveyFactoryFactory<L extends UtilsLang,
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
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyFactoryFactory.class);
	
	final Class<SURVEY> cSurvey;
	final Class<TEMPLATE> cTemplate;
	final Class<SECTION> cSection;
	final Class<QUESTION> cQuestion;
	final Class<ANSWER> cAnswer;
	final Class<DATA> cData;
    
	public EjbSurveyFactoryFactory(final Class<SURVEY> cSurvey, final Class<TEMPLATE> cTemplate, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<ANSWER> cAnswer, final Class<DATA> cData)
	{
		this.cSurvey = cSurvey;
		this.cTemplate = cTemplate;
		this.cSection = cSection;
		this.cQuestion=cQuestion;
        this.cAnswer = cAnswer;
        this.cData = cData;
	}
	
	public static <L extends UtilsLang,
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
		EjbSurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>
		factory(final Class<SURVEY> cSurvey, final Class<TEMPLATE> cTemplate, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<ANSWER> cAnswer, final Class<DATA> cData)
	{
		return new EjbSurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cSurvey,cTemplate,cSection,cQuestion,cAnswer,cData);
	}
   		
	public EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> template()
	{
		return new EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cTemplate);
	}
	
	public EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> survey()
	{
		return new EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cSurvey);
	}
	
	public EjbSurveySectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> section()
	{
		return new EjbSurveySectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cSection);
	}
	
	public EjbSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> question()
	{
		return new EjbSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cQuestion);
	}
	
	public EjbSurveyAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> answer()
	{
		return new EjbSurveyAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cAnswer);
	}
	
	public EjbSurveyDataFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> data()
	{
		return new EjbSurveyDataFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cData);
	}
}