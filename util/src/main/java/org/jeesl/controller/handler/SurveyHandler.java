package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.interfaces.model.module.survey.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveyHandler<L extends UtilsLang,
							D extends UtilsDescription,
							SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							SS extends UtilsStatus<SS,L,D>,
							SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyHandler.class);
	private static final long serialVersionUID = 1L;

	private final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION> efAnswer;
	private EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION> efData;

	private List<SECTION> sections; public List<SECTION> getSections() {return sections;}
	
	private Map<QUESTION,ANSWER> answers; public Map<QUESTION,ANSWER> getAnswers() {return answers;}
	
	private DATA surveyData; public DATA getSurveyData(){return surveyData;} public void setSurveyData(DATA surveyData) {this.surveyData = surveyData;}
	private TC category; public TC getCategory() {return category;} public void setCategory(TC category) {this.category = category;}
	
	private boolean showAssessment; public boolean isShowAssessment() {return showAssessment;}
	private boolean allowAssessment; public boolean isAllowAssessment() {return allowAssessment;} public void setAllowAssessment(boolean allowAssessment) {this.allowAssessment = allowAssessment;}
	
	public SurveyHandler(final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey, final SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION> ffSurvey)
	{
		this.fSurvey=fSurvey;
		showAssessment = false;
		allowAssessment = true;
		
		answers = new HashMap<QUESTION,ANSWER>();
		sections = new ArrayList<SECTION>();
		
		efData = ffSurvey.data();
		efAnswer = ffSurvey.answer();
	}
	
	public void reset()
	{
		showAssessment = false;
		
		sections.clear();
		answers.clear();
		surveyData = null;
	}
	
	public void prepare(SURVEY survey, CORRELATION correlation)
	{
		showAssessment = true;
		try {surveyData = fSurvey.fData(correlation);}
		catch (UtilsNotFoundException e){surveyData = efData.build(survey,correlation);}
		reloadSections(survey);
		
		if(EjbIdFactory.isSaved(surveyData)){reloadAnswers();}
		else {buildAnswers();}
	}
	
	private void reloadSections(SURVEY survey)
	{
		sections.clear();
		TEMPLATE template = fSurvey.load(survey.getTemplate());
		for(SECTION section : template.getSections())
		{
			sections.add(fSurvey.load(section));
		}
	}
	private void reloadAnswers()
	{
		answers.clear();
		for(ANSWER answer : fSurvey.fcAnswers(surveyData))
		{
			answers.put(answer.getQuestion(), answer);
		}
		for(SECTION s : sections)
		{
			for(QUESTION q : s.getQuestions())
			{
				if(!answers.containsKey(q))
				{
					answers.put(q, efAnswer.build(q, surveyData));
				}
			}
			
		}
		logger.info("Answers loaded: " + answers.size());
	}
	private void buildAnswers()
	{
		answers.clear();
		for(SECTION s : sections)
		{
			for(QUESTION q : s.getQuestions())
			{
				answers.put(q, efAnswer.build(q, surveyData));
			}
		}
		logger.info("Answers build: " + answers.size());
	}
	
	public void save(CORRELATION correlation) throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Saving "+correlation.toString());
		logger.info("CORR.saved: "+EjbIdFactory.isSaved(correlation));
		
		surveyData.setCorrelation(correlation);
		surveyData = fSurvey.saveData(surveyData);
		for(ANSWER a : answers.values())
		{
			a.setData(surveyData);
			answers.put(a.getQuestion(), fSurvey.saveAnswer(a));
		}
	}
}