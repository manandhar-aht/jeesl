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
import org.jeesl.factory.factory.survey.SurveyCoreFactoryFactory;
import org.jeesl.factory.txt.module.survey.TxtSurveyAnswerFactory;
import org.jeesl.factory.txt.module.survey.TxtSurveySectionFactory;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
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
							SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
							SS extends UtilsStatus<SS,L,D>,
							SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
							QE extends UtilsStatus<QE,L,D>,
							SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
							CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
							DOMAIN extends JeeslSurveyDomain<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
							PATH extends JeeslSurveyDomainPath<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
							DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
							ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
							AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
							AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
							ATT extends UtilsStatus<ATT,L,D>>
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyHandler.class);
	private static final long serialVersionUID = 1L;
	
	private final FacesMessageBean bMessage;
	private final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> bSurvey;
	
	private final Class<SECTION> cSection;

	private final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey;
	
	private final EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efAnswer;
	private final EjbSurveyMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efMatrix;
	private final EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efData;
	private final TxtSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> tfSection;
	private final TxtSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> tfAnswer;
	
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
	
	public static boolean debugPerformance = false;
	public static int debugDelay = 1000;
	
	public SurveyHandler(FacesMessageBean bMessage, final JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey, JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> bSurvey, final SurveyCoreFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffSurvey)
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
		
		efData = ffSurvey.data();
		efAnswer = ffSurvey.answer();
		efMatrix = ffSurvey.ejbMatrix();
		tfSection = ffSurvey.txtSection();
		tfAnswer = ffSurvey.txtAnswer();
		
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
		if(SurveyHandler.debugPerformance){logger.warn("prepare fData()");try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
		try {surveyData = fSurvey.fData(correlation);}
		catch (UtilsNotFoundException e){surveyData = efData.build(survey,correlation);}
		template = survey.getTemplate();
		if(bSurvey.getMapSection().containsKey(template)){activeSections.addAll(bSurvey.getMapSection().get(template));}
		if(SurveyHandler.debugPerformance){logger.warn("Preparing Survey for correlation:"+correlation.toString()+" and data:"+surveyData.toString());}
	}
	
	public void prepareNested(SURVEY survey, CORRELATION correlation)
	{
		showAssessment = true;
		try {surveyData = fSurvey.fData(correlation);}
		catch (UtilsNotFoundException e){surveyData = efData.build(survey,correlation);}
		template = survey.getTemplate().getNested();
		activeSections.addAll(bSurvey.getMapSection().get(template));
	}
	
	public void reloadAnswers(){reloadAnswers(true);}
	public void reloadAnswers(boolean withMatrix)
	{
		boolean dbLookup = EjbIdFactory.isSaved(surveyData);
		answers.clear();
		multiOptions.clear();
		
		if(SurveyHandler.debugPerformance){logger.warn("Reloading Answers (dbLookup:"+dbLookup+")");}
		if(dbLookup)
		{
			List<SECTION> filterSection = null;
			if(activeSections!=null && !activeSections.isEmpty()){filterSection = new ArrayList<SECTION>(activeSections);}
			if(SurveyHandler.debugPerformance){logger.warn("fAnswers for "+filterSection.size()+" "+JeeslSurveySection.class.getSimpleName()+": "+tfSection.codes(filterSection));try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
			answers = EjbSurveyAnswerFactory.toQuestionMap(fSurvey.fAnswers(surveyData, true, filterSection));
		}

		List<ANSWER> loadMatrix = new ArrayList<ANSWER>();
		if(bSurvey.getMapSection().get(template)!=null)
		{
			for(SECTION s : bSurvey.getMapSection().get(template))
			{
				if(processSection(s))
				{
					for(QUESTION q : bSurvey.getMapQuestion().get(s))
					{	
						if(q.isVisible())
						{
							ANSWER a;
							if(!answers.containsKey(q))
							{
								if(SurveyHandler.debugPerformance){logger.warn("Building new Answer for Question"+q.toString());}
								a = efAnswer.build(q, surveyData);
							}
							else
							{
								a = answers.get(q);
								if(SurveyHandler.debugPerformance){logger.warn("Using Answer "+a.getId()+" for Question "+q.toString()+" "+tfAnswer.build(a));}
							}
							if(BooleanComparator.active(q.getShowMatrix())){loadMatrix.add(a);}
							answers.put(q,a);
						}
					}
				}
			}
		}

		if(withMatrix){matrixLoader(loadMatrix);}
		
		logger.trace("Answers loaded: " + answers.size());
	}
	
	private void matrixLoader(List<ANSWER> loadMatrix)
	{
		matrix = null;
		matrix = new Nested3IdMap<MATRIX>();
		
		List<ANSWER> savedAnswer = new ArrayList<ANSWER>();
		for(ANSWER a : loadMatrix){if(EjbIdFactory.isSaved(a)){savedAnswer.add(a);}}
		
		List<MATRIX> list = fSurvey.fCells(savedAnswer);
		logger.info("MATRIX.Cells: "+list.size());
		for(MATRIX m : list)
		{
			matrix.put(m.getAnswer().getQuestion().getId(),m.getRow().getId(),m.getColumn().getId(),m);
		}
		for(ANSWER a : loadMatrix)
		{
			if(bSurvey.getMatrixRows().get(a.getQuestion())!=null && bSurvey.getMatrixCols().get(a.getQuestion())!=null)
			{
				for(OPTION row : bSurvey.getMatrixRows().get(a.getQuestion()))
				{
					for(OPTION column : bSurvey.getMatrixCols().get(a.getQuestion()))
					{
						if(!matrix.containsKey(a.getQuestion().getId(),row.getId(),column.getId()))
						{
							matrix.put(a.getQuestion().getId(),row.getId(),column.getId(),efMatrix.build(a,row,column));
						}
					}
				}
			}
		}
	}
	
	public void save(CORRELATION correlation) throws UtilsConstraintViolationException, UtilsLockingException
	{
		save(correlation,null);
	}
	public void save(CORRELATION correlation, SECTION section) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(SurveyHandler.debugPerformance){logger.warn("save");try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
		logger.info("Saving "+correlation.toString()+ " "+answers.size()+" answers  CORR.saved: "+EjbIdFactory.isSaved(correlation));
		
		surveyData.setCorrelation(correlation);
		surveyData = fSurvey.saveData(surveyData);
		
		List<ANSWER> answersToSave = new ArrayList<ANSWER>();
		for(ANSWER a : answers.values())
		{
			a.setData(surveyData);
			if(efAnswer.belongsToSection(a, section, true));
			{
				answersToSave.add(a);
				if(SurveyHandler.debugPerformance){logger.warn("\tQueing "+JeeslSurveyAnswer.class.getSimpleName()+" for Save: "+tfAnswer.build(a));}
			}
			
		}
		if(SurveyHandler.debugPerformance){logger.warn("Starting to save "+answersToSave.size()+" "+JeeslSurveyAnswer.class.getSimpleName());try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
		fSurvey.save(answersToSave);
		
		reloadAnswers(false);
		
		List<MATRIX> matrixToSave = new ArrayList<MATRIX>();
		for(ANSWER a : answers.values())
		{
			if(BooleanComparator.active(a.getQuestion().getShowMatrix()) && efAnswer.belongsToSection(a, section, true))
			{
				for(MATRIX m : matrix.values(a.getQuestion().getId()))
				{
					if(m.getOption()!=null){m.setOption(bSurvey.getMapOptionId().get(m.getOption().getId()));}
					m.setAnswer(a);
					matrixToSave.add(m);
				}
			}
		}
		if(SurveyHandler.debugPerformance){logger.warn("Save Matrix");try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
		fSurvey.save(matrixToSave);
		
		if(SurveyHandler.debugPerformance){logger.warn("Load Matrix");try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
		for(MATRIX m : fSurvey.fCells(new ArrayList<ANSWER>(answers.values())))
		{
			matrix.put(m.getAnswer().getQuestion().getId(),m.getRow().getId(),m.getColumn().getId(),m);
		}
		
		if(bMessage!=null){bMessage.growlSuccessSaved();}
	}
	
	public void onSectionChange()
	{
		if(SurveyHandler.debugPerformance){logger.warn("onSectionChange "+(activeSection!=null));try {Thread.sleep(SurveyHandler.debugDelay);} catch (InterruptedException e) {e.printStackTrace();}}
		activeSection = fSurvey.find(cSection,activeSection);
		activeSections.clear();
		activeSections.add(activeSection);
		logger.info("Section "+activeSection.toString());
		reloadAnswers();
	}
	
	private boolean processSection(SECTION s)
	{
		if(activeSection==null) {return s.isVisible();}
		
		boolean processActiveSection = activeSection!=null && s.equals(activeSection);
		return s.isVisible() && processActiveSection;
	}
	
	public String multiKey(ANSWER a)
	{
		return "x";
	}

}