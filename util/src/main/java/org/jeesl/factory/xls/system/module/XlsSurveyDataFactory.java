package org.jeesl.factory.xls.system.module;

import java.util.List;
import java.util.Map;

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
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;

public class XlsSurveyDataFactory <L extends UtilsLang, D extends UtilsDescription,
							SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							SS extends UtilsStatus<SS,L,D>,
							SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XlsSurveyDataFactory.class);
		
	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey;
	private String localeCode;
	
	public XlsSurveyDataFactory(String localeCode)
	{
		this.localeCode = localeCode;
	}
	
	public void lazy(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey)
	{
		this.fSurvey = fSurvey;
	}
	
	public void build(SURVEY survey, List<DATA> list)
	{
		//Get data for lazy loading
		TEMPLATE template = survey.getTemplate();
		if (fSurvey!=null) {template = fSurvey.load(template);}
		// The survey object holds information about the structure of the survey
		logger.info("Using survey: "+survey.getName());
		
		// Header Rendering Section
		// First level header are in the section of the survey template
		for (SECTION section : template.getSections())
		{
			logger.info("Processing first level (section) header " +section.getName());
			
			// Secondary header are nested in the individual questions of the sections
			if (fSurvey!=null) {section = fSurvey.load(section);}
			for (QUESTION question : section.getQuestions())
			{
				if (question.getShowBoolean()!= null && question.getShowBoolean()) {logger.info("Adding X to Boolean answers");}
				if (question.getShowDouble()!= null && question.getShowDouble()) {logger.info("Adding X to Double answers");}
				if (question.getShowInteger()!= null && question.getShowInteger()) {logger.info("Adding X to Integer answers");}
				if (question.getShowMatrix()!= null && question.getShowMatrix()) {logger.info("Adding X to Matrix answers");}
				if (question.getShowScore()!= null && question.getShowScore()) {logger.info("Adding X to Scores");}
				if (question.getShowSelectMulti()!= null && question.getShowSelectMulti()) {logger.info("Adding X to multiple selections");}
				if (question.getShowSelectOne()!= null && question.getShowSelectOne()) {logger.info("Adding X to single selections");}
				if (question.getShowText()!= null && question.getShowText()) {logger.info("Adding X to textural answers");}
				if (question.getShowRemark()!= null && question.getShowRemark()) {logger.info("Adding X to Remarks");}
			}
		}
		
		// Data Rendering Section
		// Individual Answers
		for (DATA surveyData : list)
		{
			if (fSurvey!=null) {surveyData = fSurvey.load(surveyData);}
			logger.info("Survey data " +surveyData.getAnswers().size() +" answers.");
			Map<QUESTION, ANSWER> infoInventory = EjbSurveyAnswerFactory.toQuestionMap(surveyData.getAnswers());
			for (QUESTION question : infoInventory.keySet())
			{
				logger.info("Answer to question: " +question.getQuestion());
				ANSWER answer = infoInventory.get(question);
				if (fSurvey!=null) {answer = fSurvey.load(answer);}
				if (question.getShowBoolean()!= null && question.getShowBoolean() && answer.getValueBoolean()!=null) {logger.info(answer.getValueBoolean().toString());}
				if (question.getShowDouble()!= null && question.getShowDouble() && answer.getValueDouble()!=null) {logger.info(answer.getValueDouble().toString());}
				if (question.getShowInteger()!= null && question.getShowInteger() && answer.getValueNumber()!=null) {logger.info(answer.getValueNumber().toString());}
				if (question.getShowMatrix()!= null && question.getShowMatrix() && answer.getMatrix() != null) {logger.info(answer.getMatrix().toString());}
				if (question.getShowScore()!= null && question.getShowScore() && answer.getScore()!=null) {logger.info(answer.getScore().toString());}
				if (question.getShowSelectMulti()!= null && question.getShowSelectMulti() && answer.getOptions()!=null) {logger.info(answer.getOptions().toString());}
				if (question.getShowSelectOne()!= null && question.getShowSelectOne() && answer.getOption()!=null) {logger.info(answer.getOption().getCode());}
				if (question.getShowText()!= null && question.getShowText() && answer.getValueText()!=null) {logger.info(answer.getValueText());}
				if (question.getShowRemark()!= null && question.getShowRemark() && answer.getRemark()!=null) {logger.info(answer.getRemark());}
			}
		}
	}
}