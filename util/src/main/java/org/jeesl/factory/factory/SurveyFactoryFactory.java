package org.jeesl.factory.factory;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.handler.SurveyHandler;
import org.jeesl.controller.processor.survey.SurveyScoreProcessor;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyMatrixFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionSetFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySchemeFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyScoreFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateVersionFactory;
import org.jeesl.factory.json.system.survey.JsonSurveyFactory;
import org.jeesl.factory.txt.module.survey.TxtOptionFactory;
import org.jeesl.factory.txt.module.survey.TxtSurveyAnswerFactory;
import org.jeesl.factory.txt.module.survey.TxtSurveyQuestionFactory;
import org.jeesl.factory.txt.module.survey.TxtSurveySectionFactory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.util.comparator.ejb.module.survey.SurveyAnswerComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveyFactoryFactory<L extends UtilsLang, D extends UtilsDescription,
									SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									SS extends UtilsStatus<SS,L,D>,
									SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									TS extends UtilsStatus<TS,L,D>,
									TC extends UtilsStatus<TC,L,D>,
									SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
									CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyFactoryFactory.class);
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<SURVEY> cSurvey;
	private final Class<SCHEME> cScheme;
	private final Class<TEMPLATE> cTemplate;
	private final Class<VERSION> cVersion;
	private final Class<SECTION> cSection; public Class<SECTION> getClassSection() {return cSection;}
	private final Class<QUESTION> cQuestion;
	private final Class<SCORE> cScore;
	private final Class<ANSWER> cAnswer;
	private final Class<MATRIX> cMatrix;
	private final Class<DATA> cData;
	private final Class<OPTIONS> cOptions;
	private final Class<OPTION> cOption; public Class<OPTION> getOptionClass() {return cOption;}

	public SurveyFactoryFactory(final Class<L> cL, final Class<D> cD, final Class<SURVEY> cSurvey, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption)
	{
		this.cL = cL;
		this.cD = cD;
		this.cSurvey = cSurvey;
		this.cScheme = cScheme;
		this.cTemplate = cTemplate;
		this.cVersion = cVersion;
		this.cSection = cSection;
		this.cQuestion = cQuestion;
		this.cScore = cScore;
        this.cAnswer = cAnswer;
        this.cMatrix = cMatrix;
        this.cData = cData;
        this.cOptions = cOptions;
        this.cOption = cOption;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					SS extends UtilsStatus<SS,L,D>,
					SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>, 
					TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					TS extends UtilsStatus<TS,L,D>,
					TC extends UtilsStatus<TC,L,D>,
					SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>>
		SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>
		factory(final Class<L> cL, final Class<D> cD, final Class<SURVEY> cSurvey, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption)
	{
		return new SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cL,cD,cSurvey,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cAnswer,cMatrix,cData,cOptions,cOption);
	}
	
	public EjbSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> survey()
	{
		return new EjbSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cL,cD,cSurvey);
	}
	
	public EjbSurveySchemeFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> scheme()
	{
		return new EjbSurveySchemeFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cScheme);
	}
   		
	public EjbSurveyTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> template()
	{
		return new EjbSurveyTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cTemplate);
	}
	
	public EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> version()
	{
		return new EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cVersion);
	}
	
	public EjbSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> section()
	{
		return new EjbSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cSection);
	}
	
	public EjbSurveyQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> question()
	{
		return new EjbSurveyQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cQuestion);
	}
	
	public EjbSurveyScoreFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> score()
	{
		return new EjbSurveyScoreFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cScore);
	}
	
	public EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> answer()
	{
		return new EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cAnswer);
	}
	
	public EjbSurveyMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> ejbMatrix()
	{
		return new EjbSurveyMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cMatrix);
	}
	
	public EjbSurveyOptionSetFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> optionSet()
	{
		return new EjbSurveyOptionSetFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cOptions);
	}
	
	public EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> option()
	{
		return new EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cOption);
	}
	
	public EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> data()
	{
		return new EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(cData);
	}
	
	public TxtSurveyQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> txtQuestion()
	{
		return new TxtSurveyQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>();
	}
	
	public TxtSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> txtSection()
	{
		return new TxtSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>();
	}
	
	public TxtSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> txtAnswer()
	{
		return new TxtSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>();
	}
	
	public TxtOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> txtOption(String localeCode)
	{
		return new TxtOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode);
	}
	
	public SurveyScoreProcessor<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> scoreProcessor()
	{
		return new SurveyScoreProcessor<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>();
	}
	
	public SurveyHandler<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> handler(FacesMessageBean bMessage, final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey, JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> bSurvey)
	{
		return new SurveyHandler<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(bMessage,fSurvey,bSurvey,this);
	}
	
	public JsonSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> surveyJson(String localeCode, org.jeesl.model.json.survey.Survey q)
	{
		return new JsonSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,q);
	}
	
	/*
	public XlsSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xlsData0(String localeCode)
	{
		return new XlsSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,option());
	}
	
	
	public XlsSurveyData2Factory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xlsData2(String localeCode)
	{
		return new XlsSurveyData2Factory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,template(),option());
	}
	*/
	public SurveyAnswerComparator<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> cmpAnswer()
	{
		return new SurveyAnswerComparator<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>();
	}
}