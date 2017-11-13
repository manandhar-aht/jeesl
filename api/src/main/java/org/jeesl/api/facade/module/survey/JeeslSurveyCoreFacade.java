package org.jeesl.api.facade.module.survey;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.JeeslWithSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurveyType;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;
import org.jeesl.model.json.JsonFlatFigures;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSurveyCoreFacade <L extends UtilsLang, D extends UtilsDescription,
									SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
									SS extends UtilsStatus<SS,L,D>,
									SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
									TS extends UtilsStatus<TS,L,D>,
									TC extends UtilsStatus<TC,L,D>,
									SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
									QE extends UtilsStatus<QE,L,D>,
									SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
									MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
									DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
									OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
									OPTION extends JeeslSurveyOption<L,D>,
									CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>>
	extends UtilsFacade
{	
	TEMPLATE load(TEMPLATE template, boolean withQuestions, boolean withOptions);
	SECTION load(SECTION section);
	SURVEY load(SURVEY survey);
	QUESTION load(QUESTION question);
	ANSWER load(ANSWER answer);
	DATA load(DATA data);
	OPTIONS load(OPTIONS optionSet);
	
	List<SURVEY> fSurveysForCategories(List<TC> categories);
	
	OPTION saveOption(QUESTION question, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	OPTION saveOption(OPTIONS set, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	
	void rmVersion(VERSION version) throws UtilsConstraintViolationException;
	void rmOption(QUESTION question, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	void rmOption(OPTIONS set, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	

	
	SURVEY fSurvey(CORRELATION correlation) throws UtilsNotFoundException;
	void deleteSurvey(SURVEY survey) throws UtilsConstraintViolationException, UtilsLockingException;
	
	List<SURVEY> fSurveys(TC category, SS status, Date date);
	List<SURVEY> fSurveys(List<TC> categories, SS status, Date date);
	<W extends JeeslWithSurvey<SURVEY>> List<W> fSurveys(Class<W> c, List<SS> status, Date date);
	<TYPE extends UtilsStatus<TYPE,L,D>, WT extends JeeslWithType<L,D,TYPE>, W extends JeeslWithSurveyType<SURVEY,WT,TYPE>> List<W> fWithSurveys(Class<W> c, List<SS> status, TYPE type, Date date);
	<W extends JeeslWithSurvey<SURVEY>> W fWithSurvey(Class<W> c, long id) throws UtilsNotFoundException;
	List<VERSION> fVersions(TC category, Long refId);
	
	List<ANSWER> fcAnswers(DATA data);
	List<ANSWER> fAnswers(SURVEY survey);
	List<ANSWER> fAnswers(SURVEY survey, QUESTION question);
	List<ANSWER> fAnswers(DATA data, Boolean visible, List<SECTION> sections);
	List<ANSWER> fAnswers(List<DATA> datas);
	List<MATRIX> fCells(List<ANSWER> answers);
	
	DATA fData(CORRELATION correlation) throws UtilsNotFoundException;
	List<DATA> fDatas(List<CORRELATION> correlations);
	DATA saveData(DATA data) throws UtilsConstraintViolationException, UtilsLockingException;
	
	ANSWER saveAnswer(ANSWER answer) throws UtilsConstraintViolationException, UtilsLockingException;
	void rmAnswer(ANSWER answer) throws UtilsConstraintViolationException;
	

}