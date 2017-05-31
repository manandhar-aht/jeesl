package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyMatrixFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.factory.txt.module.survey.TxtOptionFactory;
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
import org.jeesl.model.pojo.map.Nested3Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.util.comparator.ejb.PositionComparator;
import net.sf.exlp.util.io.StringUtil;

public class SurveyHandler<L extends UtilsLang,
							D extends UtilsDescription,
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
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyHandler.class);
	private static final long serialVersionUID = 1L;
	
	private final FacesMessageBean bMessage;
	final Class<OPTION> cOption;

	private final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey;
	
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efAnswer;
	private EjbSurveyMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efMatrix;
	private EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efData;
	private EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efOption;

	private final TxtOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> tfOption;
	
	private List<SECTION> sections; public List<SECTION> getSections() {return sections;}
	
	private Map<QUESTION,ANSWER> answers; public Map<QUESTION,ANSWER> getAnswers() {return answers;}
	private Map<QUESTION,List<OPTION>> matrixRows; public Map<QUESTION,List<OPTION>> getMatrixRows() {return matrixRows;}
	private Map<QUESTION,List<OPTION>> matrixCols; public Map<QUESTION,List<OPTION>> getMatrixCols() {return matrixCols;}
	private Map<QUESTION,List<OPTION>> matrixCells; public Map<QUESTION,List<OPTION>> getMatrixCells() {return matrixCells;}
	private Nested3Map<QUESTION,OPTION,OPTION,MATRIX> matrix; public Nested3Map<QUESTION,OPTION,OPTION,MATRIX> getMatrix() {return matrix;}

	private DATA surveyData; public DATA getSurveyData(){return surveyData;} public void setSurveyData(DATA surveyData) {this.surveyData = surveyData;}
	private TC category; public TC getCategory() {return category;} public void setCategory(TC category) {this.category = category;}
	
	private Comparator<OPTION> cmpOption;
	
	private boolean showAssessment; public boolean isShowAssessment() {return showAssessment;}
	private boolean allowAssessment; public boolean isAllowAssessment() {return allowAssessment;} public void setAllowAssessment(boolean allowAssessment) {this.allowAssessment = allowAssessment;}
	
	public SurveyHandler(FacesMessageBean bMessage, final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey, final SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> ffSurvey)
	{
		this.bMessage=bMessage;
		this.fSurvey=fSurvey;
		showAssessment = false;
		allowAssessment = true;
		
		answers = new HashMap<QUESTION,ANSWER>();
		matrix = new Nested3Map<QUESTION,OPTION,OPTION,MATRIX>();
		matrixRows = new HashMap<QUESTION,List<OPTION>>();
		matrixCols = new HashMap<QUESTION,List<OPTION>>();
		matrixCells = new HashMap<QUESTION,List<OPTION>>();
		sections = new ArrayList<SECTION>();
		
		cOption = ffSurvey.getOptionClass();
		efData = ffSurvey.data();
		efAnswer = ffSurvey.answer();
		efMatrix = ffSurvey.ejbMatrix();
		efOption = ffSurvey.option();
		tfOption = ffSurvey.txtOption("en");
		
		cmpOption = new PositionComparator<OPTION>();
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
		reloadAnswers(EjbIdFactory.isSaved(surveyData));
	}
	
	private void reloadSections(SURVEY survey)
	{
		sections.clear();
		matrixRows.clear();
		matrixCols.clear();
		matrixCells.clear();
		
		TEMPLATE template = fSurvey.load(survey.getTemplate());
		for(SECTION section : template.getSections())
		{
			section = fSurvey.load(section);
			sections.add(section);
			for(QUESTION question : section.getQuestions())
			{
				if(question.getShowMatrix()!=null && question.getShowMatrix())
				{
					Collections.sort(question.getOptions(),cmpOption);
					matrixRows.put(question, efOption.toRows(question.getOptions()));
					matrixCols.put(question, efOption.toColumns(question.getOptions()));
					matrixCells.put(question, efOption.toCells(question.getOptions()));
					
					if(logger.isTraceEnabled())
					{
						logger.trace(StringUtil.stars());
						logger.trace(question.getCode());
						logger.trace("\tRows"+tfOption.labels(matrixRows.get(question)));
						logger.trace("\tColumns"+tfOption.labels(matrixCols.get(question)));
						logger.trace("\tCells"+tfOption.labels(matrixCells.get(question)));
					}
				}
			}
		}
	}
	private void reloadAnswers(boolean dbLookup)
	{
		answers.clear();
		matrix.clear();
		
		if(dbLookup)
		{
			for(ANSWER answer : fSurvey.fcAnswers(surveyData))
			{
				answers.put(answer.getQuestion(), answer);
			}
		}

		for(SECTION s : sections)
		{
			for(QUESTION q : s.getQuestions())
			{
				if(!answers.containsKey(q))
				{
					answers.put(q, efAnswer.build(q, surveyData));
				}
				buildMatrix(dbLookup,answers.get(q));
			}
			
		}
		logger.info("Answers loaded: " + answers.size());
	}
	
	private void buildMatrix(boolean dbLookup, ANSWER answer)
	{
		if(answer.getQuestion().getShowMatrix()!=null && answer.getQuestion().getShowMatrix())
		{
			if(dbLookup){answer = fSurvey.load(answer);}			
			for(MATRIX m : answer.getMatrix())
			{
				matrix.put(answer.getQuestion(),m.getRow(),m.getColumn(),m);
			}
			for(OPTION row : matrixRows.get(answer.getQuestion()))
			{
				for(OPTION column : matrixCols.get(answer.getQuestion()))
				{
					if(!matrix.containsKey(answer.getQuestion(),row,column))
					{
						matrix.put(answer.getQuestion(),row,column,efMatrix.build(answer,row,column));
					}
				}
			}
			logger.warn("Building Matrix for "+answer.toString());
		}
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
			a = fSurvey.saveAnswer(a);
			if(a.getQuestion().getShowMatrix()!=null && a.getQuestion().getShowMatrix())
			{
				List<MATRIX> list = new ArrayList<MATRIX>();;
				for(MATRIX m : matrix.values(a.getQuestion()))
				{
					if(m.getOption()!=null){m.setOption(fSurvey.find(cOption,m.getOption()));}
					m.setAnswer(a);
					m = fSurvey.save(m);
					list.add(m);
					matrix.put(a.getQuestion(),m.getRow(),m.getColumn(),m);
				}
				a.setMatrix(list);
			}
			answers.put(a.getQuestion(), a);
		}
		if(bMessage!=null){bMessage.growlSuccessSaved();}
	}
}