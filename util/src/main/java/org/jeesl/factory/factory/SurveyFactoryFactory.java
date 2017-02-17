package org.jeesl.factory.factory;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.handler.SurveyHandler;
import org.jeesl.controller.processor.SurveyScoreProcessor;
import org.jeesl.factory.ejb.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateVersionFactory;
import org.jeesl.factory.txt.survey.TxtSurveyQuestionFactory;
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

public class SurveyFactoryFactory<L extends UtilsLang,
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
	final static Logger logger = LoggerFactory.getLogger(SurveyFactoryFactory.class);
	
	final Class<SURVEY> cSurvey;
	final Class<TEMPLATE> cTemplate;
	final Class<VERSION> cVersion;
	final Class<SECTION> cSection;
	final Class<QUESTION> cQuestion;
	final Class<ANSWER> cAnswer;
	final Class<DATA> cData;
	final Class<OPTION> cOption;
    
	public SurveyFactoryFactory(final Class<SURVEY> cSurvey, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<ANSWER> cAnswer, final Class<DATA> cData, final Class<OPTION> cOption)
	{
		this.cSurvey = cSurvey;
		this.cTemplate = cTemplate;
		this.cVersion = cVersion;
		this.cSection = cSection;
		this.cQuestion=cQuestion;
        this.cAnswer = cAnswer;
        this.cData = cData;
        this.cOption = cOption;
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
		SurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>
		factory(final Class<SURVEY> cSurvey, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<ANSWER> cAnswer, final Class<DATA> cData, final Class<OPTION> cOption)
	{
		return new SurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cSurvey,cTemplate,cVersion,cSection,cQuestion,cAnswer,cData,cOption);
	}
	
	public EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> survey()
	{
		return new EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cSurvey);
	}
   		
	public EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> template()
	{
		return new EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cTemplate);
	}
	
	public EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> version()
	{
		return new EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cVersion);
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
	
	public EjbSurveyOptionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> option()
	{
		return new EjbSurveyOptionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cOption);
	}
	
	public EjbSurveyDataFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> data()
	{
		return new EjbSurveyDataFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cData);
	}
	
	public TxtSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> txtQuestion()
	{
		return new TxtSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>();
	}
	
	public SurveyScoreProcessor<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> scoreProcessor()
	{
		return new SurveyScoreProcessor<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>();
	}
	
	public SurveyHandler<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> handler(final JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey)
	{
		return new SurveyHandler<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(fSurvey,this);
	}
}