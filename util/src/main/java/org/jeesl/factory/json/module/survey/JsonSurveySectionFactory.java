package org.jeesl.factory.json.module.survey;

import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;
import org.jeesl.model.json.survey.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JsonSurveySectionFactory<L extends UtilsLang,D extends UtilsDescription,
				SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
				VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,
				SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SECTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
				CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
				VALIDATION extends JeeslSurveyValidation<QUESTION>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,OPTION extends JeeslSurveyOption<L,D>,
				CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveySectionFactory.class);
	
	private final String localeCode;
	private final Section q;
	
	private JeeslSurveyCoreFacade<L,D,?,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey;
	private JsonSurveyQuestionFactory<L,D,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION> jfQuestion;
	
	public JsonSurveySectionFactory(String localeCode, Section q){this(localeCode,q,null,null);}
	public JsonSurveySectionFactory(String localeCode, Section q,
			SurveyTemplateFactoryBuilder<L,D,?,?,?,?,?,?,?,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
			JeeslSurveyCoreFacade<L,D,?,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey)
	{
		this.localeCode=localeCode;
		this.q=q;
		this.fSurvey=fSurvey;
		if(!q.getQuestions().isEmpty()){jfQuestion = new JsonSurveyQuestionFactory<L,D,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION>(localeCode,q.getQuestions().get(0),fbTemplate,fSurvey);}
	}
	
	public Section build(SECTION ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(ejb);}
		Section json = build();
		
		json.setId(ejb.getId());
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetName()){json.setName(ejb.getName().get(localeCode).getLang());}
		
		for(QUESTION q : ejb.getQuestions())
		{
			if(q.isVisible())
			{
				json.getQuestions().add(jfQuestion.build(q));
			}
		}
		
		return json;
	}
	
	public static Section build() {return new Section();}
	public static Section id(long id)
	{
		Section json = build();
		json.setId(id);
		return json;
	}
}