package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyMatrixFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
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
import org.jeesl.model.pojo.map.id.Nested3IdMap;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveyHandler<L extends UtilsLang, D extends UtilsDescription,
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
	private final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> bSurvey;
	
	private final Class<SECTION> cSection;
	private final Class<OPTION> cOption;
	
//	private final Comparator<ANSWER> cpAnswer;

	private final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey;
	
//	private final EjbSurveyQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efQuestion;
	private final EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efAnswer;
	private final EjbSurveyMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efMatrix;
	private final EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efData;
	
	private List<OPTION> districts; public List<OPTION> getDistricts() {return districts;} public void setDistricts(List<OPTION> districts) {this.districts = districts;}
	
	private Map<QUESTION,ANSWER> answers; public Map<QUESTION,ANSWER> getAnswers() {return answers;}
	private Map<QUESTION,Object> multiOptions; public Map<QUESTION,Object> getMultiOptions() {return multiOptions;}
	private Nested3IdMap<MATRIX> matrix; public Nested3IdMap<MATRIX> getMatrix() {return matrix;}

	private TEMPLATE template; public TEMPLATE getTemplate() {return template;}
	private DATA surveyData; public DATA getSurveyData(){return surveyData;} public void setSurveyData(DATA surveyData) {this.surveyData = surveyData;}
	private TC category; public TC getCategory() {return category;} public void setCategory(TC category) {this.category = category;}
	
	private Set<SECTION> activeSections;
	private SECTION activeSection; public SECTION getActiveSection() {return activeSection;} public void setActiveSection(SECTION activeSection) {this.activeSection = activeSection;}

	private boolean showAssessment; public boolean isShowAssessment() {return showAssessment;}
	private boolean allowAssessment; public boolean isAllowAssessment() {return allowAssessment;} public void setAllowAssessment(boolean allowAssessment) {this.allowAssessment = allowAssessment;}
	
	public SurveyHandler(FacesMessageBean bMessage, final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey, JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> bSurvey, final SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> ffSurvey)
	{
		this.bMessage=bMessage;
		this.fSurvey=fSurvey;
		this.bSurvey=bSurvey;
		
		showAssessment = false;
		allowAssessment = true;
		
		answers = new HashMap<QUESTION,ANSWER>();
		matrix = new Nested3IdMap<MATRIX>();
		multiOptions = new HashMap<QUESTION,Object>();
		
		cSection = ffSurvey.getClassSection();
		cOption = ffSurvey.getOptionClass();
		
		efData = ffSurvey.data();
		efAnswer = ffSurvey.answer();
		efMatrix = ffSurvey.ejbMatrix();
		
//		cpAnswer = ffSurvey.cmpAnswer().factory(SurveyAnswerComparator.Type.position);
		
		activeSections = new HashSet<SECTION>();
	}
	
	public void reset()
	{
		answers.clear();
		matrix.clear();
		multiOptions.clear();
		activeSections.clear();
		surveyData = null;
		showAssessment = false;
	}
	
	public void prepare(SURVEY survey, CORRELATION correlation)
	{
		showAssessment = true;
		try {surveyData = fSurvey.fData(correlation);}
		catch (UtilsNotFoundException e){surveyData = efData.build(survey,correlation);}
		template = survey.getTemplate();
		activeSections.addAll(bSurvey.getMapSection().get(template));
		logger.info("Preparing Survey for correlation:"+correlation.toString()+" and data:"+surveyData.toString());
		reloadAnswers(EjbIdFactory.isSaved(surveyData));
	}
	
	public void prepareNested(SURVEY survey, CORRELATION correlation)
	{
		showAssessment = true;
		try {surveyData = fSurvey.fData(correlation);}
		catch (UtilsNotFoundException e){surveyData = efData.build(survey,correlation);}
		template = survey.getTemplate().getNested();
		activeSections.addAll(bSurvey.getMapSection().get(template));
		reloadAnswers(EjbIdFactory.isSaved(surveyData));
	}
	
	private void reloadAnswers(boolean dbLookup)
	{
		answers.clear();
		matrix = null;
		matrix = new Nested3IdMap<MATRIX>();
		multiOptions.clear();
		
		if(logger.isTraceEnabled()){logger.info("Reloading Answers");}
		if(dbLookup)
		{
			List<SECTION> filterSection = null;
			if(activeSections!=null && !activeSections.isEmpty()){filterSection = new ArrayList<SECTION>(activeSections);}
			answers = EjbSurveyAnswerFactory.toQuestionMap(fSurvey.fAnswers(surveyData, true, filterSection));
			
/*			List<ANSWER> l = fSurvey.fcAnswers(surveyData);
			Collections.sort(l,cpAnswer);
			for(ANSWER answer : l)
			{
				if(logger.isTraceEnabled()){logger.info("\tAnswer: "+answer.toString());}
				if(answer.getQuestion().getSection().isVisible() && answer.getQuestion().isVisible() && activeSection!=null && answer.getQuestion().getSection().equals(activeSection))
				{
					answers.put(answer.getQuestion(), answer);
				}
			}
*/		}

		for(SECTION s : bSurvey.getMapSection().get(template))
		{
			if(processSection(s))
			{
				for(QUESTION q : bSurvey.getMapQuestion().get(s))
				{	
					if(q.isVisible())
					{
						if(logger.isTraceEnabled()){logger.info("Building answer for "+q.toString());}
						ANSWER a;
						if(!answers.containsKey(q)){a = efAnswer.build(q, surveyData);}
						else {a = answers.get(q);}
						a = buildMatrix(dbLookup,a);
						answers.put(q,a);
					}
				}
			}
		}
		logger.info("Answers loaded: " + answers.size());
	}
	
	private ANSWER buildMatrix(boolean dbLookup, ANSWER answer)
	{
		boolean isMulti = answer.getQuestion().getShowSelectMulti()!=null && answer.getQuestion().getShowSelectMulti();
		boolean isMatrix = answer.getQuestion().getShowMatrix()!=null && answer.getQuestion().getShowMatrix();

		if(dbLookup && (isMulti || isMatrix)){answer = fSurvey.load(answer);}
		
		if(isMulti)
		{
			List<OPTION> list = new ArrayList<OPTION>();
			list.addAll(answer.getOptions());
			multiOptions.put(answer.getQuestion(),list);
		}
		
		if(isMatrix)
		{
			for(MATRIX m : answer.getMatrix())
			{
				matrix.put(answer.getQuestion().getId(),m.getRow().getId(),m.getColumn().getId(),m);
			}
			for(OPTION row : bSurvey.getMatrixRows().get(answer.getQuestion()))
			{
				for(OPTION column : bSurvey.getMatrixCols().get(answer.getQuestion()))
				{
					if(!matrix.containsKey(answer.getQuestion().getId(),row.getId(),column.getId()))
					{
						matrix.put(answer.getQuestion().getId(),row.getId(),column.getId(),efMatrix.build(answer,row,column));
					}
				}
			}
			logger.warn("Building Matrix for "+answer.toString());
		}
		return answer;
	}
	
	public void save(CORRELATION correlation) throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Saving "+correlation.toString()+ " "+answers.size());
		logger.info("CORR.saved: "+EjbIdFactory.isSaved(correlation));
		
		surveyData.setCorrelation(correlation);
		surveyData = fSurvey.saveData(surveyData);
		for(ANSWER a : answers.values())
		{
			a.setData(surveyData);
			if(a.getQuestion().isVisible() && processSection(a.getQuestion().getSection()))
			{
				logger.info("Saving: "+a.getId()+" for question: "+a.getQuestion().toString()+" ");
				boolean withMulti = BooleanComparator.active(a.getQuestion().getShowSelectMulti());
				
				if(withMulti)
				{
					boolean mapHasMulti = multiOptions.containsKey(a.getQuestion());
					
					logger.info("Multi-Options:"+withMulti+" map:"+mapHasMulti+" for Question "+a.getQuestion().toString());
					a.getOptions().clear();
					if(mapHasMulti)
					{
	/*					logger.info("Content of MultiOPtions:" +multiOptions.get(a.getQuestion()).getClass().getName());
						Object obj = multiOptions.get(a.getQuestion());
						List<OPTION> list = (List<OPTION>)obj;
						
						logger.info("toString:" +obj.toString());
						
						logger.info("Content of MultiOPtions:" +multiOptions.get(a.getQuestion()).getClass().getName());
						logger.info("Content of MultiOPtions:" +multiOptions.get(a.getQuestion()).toString());
						a.getOptions().addAll(list);
	*/				}
					else{logger.warn("No Multi-Options for Questison "+a.getQuestion().getCode());}
					
					logger.info("Multi: "+a.getOptions().size());
				}
				a = fSurvey.saveAnswer(a);
				if(a.getQuestion().getShowMatrix()!=null && a.getQuestion().getShowMatrix())
				{
					List<MATRIX> list = new ArrayList<MATRIX>();
					for(MATRIX m : matrix.values(a.getQuestion().getId()))
					{
						if(m.getOption()!=null){m.setOption(fSurvey.find(cOption,m.getOption()));}
						m.setAnswer(a);
						m = fSurvey.save(m);
						list.add(m);
						matrix.put(a.getQuestion().getId(),m.getRow().getId(),m.getColumn().getId(),m);
					}
					a.setMatrix(list);
				}
				
				answers.put(a.getQuestion(), a);
			}
		}
		if(bMessage!=null){bMessage.growlSuccessSaved();}
	}
	
	public void onSectionChange()
	{
		logger.info("onSectionChange" + (activeSection!=null));
		activeSection = fSurvey.find(cSection,activeSection);
		activeSections.clear();
		activeSections.add(activeSection);
		logger.info("Section "+activeSection.toString());
		reloadAnswers(EjbIdFactory.isSaved(surveyData));
	}
	
	private boolean processSection(SECTION s)
	{
		boolean processActiveSection = activeSection!=null && s.equals(activeSection);
		
		return s.isVisible() && processActiveSection;
	}
}