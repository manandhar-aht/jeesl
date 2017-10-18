package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.monitor.ProcessingTimeTracker;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySectionFactory;
import org.jeesl.factory.factory.survey.SurveyAnalysisFactoryFactory;
import org.jeesl.factory.factory.survey.SurveyCoreFactoryFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAppSurveyBean <L extends UtilsLang, D extends UtilsDescription,
						SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						SS extends UtilsStatus<SS,L,D>,
						SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
						TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS>,
						VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
						TS extends UtilsStatus<TS,L,D>,
						TC extends UtilsStatus<TC,L,D>,
						SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,AQ>,
						QE extends UtilsStatus<QE,L,D>,
						SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE>,
						UNIT extends UtilsStatus<UNIT,L,D>,
						ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,VERSION,SECTION,QUESTION,ANSWER,MATRIX,DATA,OPTIONS,OPTION>,
						OPTION extends JeeslSurveyOption<L,D>,
						CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						DOMAIN extends JeeslSurveyDomain<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
						PATH extends JeeslSurveyDomainPath<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
						DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
						ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, ATT extends UtilsStatus<ATT,L,D>>
					implements Serializable,
								JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppSurveyBean.class);

	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey;
	private final SurveyCoreFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffSurvey;
	private final SurveyAnalysisFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffAnalysis;
	
	protected final EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efOption;
	protected final EjbSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efSection;
	
	protected Map<TEMPLATE,List<SECTION>> mapSection; @Override public Map<TEMPLATE,List<SECTION>> getMapSection() {return mapSection;}
	protected Map<SECTION,List<QUESTION>> mapQuestion; @Override public Map<SECTION,List<QUESTION>> getMapQuestion() {return mapQuestion;}

	protected Map<Long,OPTION> mapOptionId; @Override public Map<Long,OPTION> getMapOptionId(){return mapOptionId;}
	protected Map<QUESTION,List<OPTION>> mapOption; @Override public Map<QUESTION,List<OPTION>> getMapOption() {return mapOption;}
	protected Map<OPTIONS,List<OPTION>> mapOptionSet;

	protected Map<QUESTION,List<OPTION>> matrixRows; @Override public Map<QUESTION,List<OPTION>> getMatrixRows() {return matrixRows;}
	protected Map<QUESTION,List<OPTION>> matrixCols; @Override public Map<QUESTION,List<OPTION>> getMatrixCols() {return matrixCols;}
	protected Map<QUESTION,List<OPTION>> matrixCells; public Map<QUESTION,List<OPTION>> getMatrixCells() {return matrixCells;}

	public AbstractAppSurveyBean(SurveyCoreFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffSurvey, SurveyAnalysisFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffAnalysis)
	{
		this.ffSurvey=ffSurvey;
		this.ffAnalysis=ffAnalysis;
		efOption = ffSurvey.option();
		efSection = ffSurvey.section();
		
		mapSection = new HashMap<TEMPLATE,List<SECTION>>();
		mapQuestion = new HashMap<SECTION,List<QUESTION>>();
		
		mapOptionId = new HashMap<Long,OPTION>();
		mapOption = new HashMap<QUESTION,List<OPTION>>();
		mapOptionSet = new HashMap<OPTIONS,List<OPTION>>();
		matrixRows = new HashMap<QUESTION,List<OPTION>>();
		
		matrixCols = new HashMap<QUESTION,List<OPTION>>();
		matrixCells = new HashMap<QUESTION,List<OPTION>>();
	}
	
	public void initSuper(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey)
	{
		this.fSurvey=fSurvey;
		refreshUnits();
		refreshSurveyStatus();
		reloadToolTypes();
		
		//Cache
		mapSection.clear();mapSection.putAll(efSection.loadMap(fSurvey));
		reloadSets();
		reloadQuestions();
//		reloadOptions();
	}
	
	public void reloadQuestionElements() {}
	
	private List<ATT> toolTypes;
	public List<ATT> getToolTypes(){return toolTypes;}
	public void reloadToolTypes(){toolTypes=fSurvey.allOrderedPositionVisible(ffAnalysis.getAttClass());}
	
	private List<UNIT> units;
	public List<UNIT> getUnits(){return units;}
	public void refreshUnits(){units=fSurvey.allOrderedPositionVisible(ffSurvey.getClassUnit());}
	
	private List<SS> surveyStatus;
	public List<SS> getSurveyStatus(){return surveyStatus;}
	public void refreshSurveyStatus(){surveyStatus=fSurvey.allOrderedPositionVisible(ffSurvey.getClassSurveyStatus());}
	
	@Override public void updateTemplate(TEMPLATE template)
	{
		if(!mapSection.containsKey(template)){mapSection.put(template,new ArrayList<SECTION>());}
		mapSection.get(template).clear();
		for(SECTION section : template.getSections())
		{
			if(section.isVisible())
			{
				mapSection.get(template).add(section);
			}
		}
	}
	
	private void reloadSets()
	{
		mapOptionSet.clear();
		for(OPTIONS set : fSurvey.allOrderedPosition(ffSurvey.getOptionSetClass()))
		{
			set = fSurvey.load(set);
			if(!mapOptionSet.containsKey(set)){mapOptionSet.put(set,new ArrayList<OPTION>());}
			for(OPTION o : set.getOptions()) {mapOptionId.put(o.getId(),o);}
			mapOptionSet.get(set).addAll(set.getOptions());
		}
	}
		
	private void reloadQuestions()
	{
		// A similar method is available in EjbQuestionFactory
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		mapQuestion.clear();
		mapOption.clear();
		for(QUESTION q : fSurvey.allOrderedPosition(ffSurvey.getClassQuestion()))
		{
			q = fSurvey.load(q);
			if(!mapQuestion.containsKey(q.getSection())){mapQuestion.put(q.getSection(),new ArrayList<QUESTION>());}
			mapQuestion.get(q.getSection()).add(q);
			updateOptions(q);
		}
		logger.info(ffSurvey.getClassQuestion().getSimpleName()+" loaded in "+AbstractLogMessage.postConstruct(ptt));
	}
/*	
	private void reloadOptions()
	{
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		mapOption.clear();
		for(OPTION o : fSurvey.allOrderedPosition(ffSurvey.getOptionClass()))
		{
			updateCache(o);
		}
		logger.info("Options loaded in "+AbstractLogMessage.postConstruct(ptt));
	}
	
	private void updateCache(OPTION o)
	{
		mapOptionId.put(o.getId(),o);
		if(!mapOption.containsKey(o.getQuestion())){mapOption.put(o.getQuestion(),new ArrayList<OPTION>());}
		mapOption.get(o.getQuestion()).add(o);
		
		if(o.getRow())
		{
			if(!matrixRows.containsKey(o.getQuestion())){matrixRows.put(o.getQuestion(),new ArrayList<OPTION>());}
			matrixRows.get(o.getQuestion()).add(o);
		}
		if(o.getCol())
		{
			if(!matrixCols.containsKey(o.getQuestion())){matrixCols.put(o.getQuestion(),new ArrayList<OPTION>());}
			matrixCols.get(o.getQuestion()).add(o);
		}
		if(o.getCell())
		{
			if(!matrixCells.containsKey(o.getQuestion())){matrixCells.put(o.getQuestion(),new ArrayList<OPTION>());}
			matrixCells.get(o.getQuestion()).add(o);
		}
	}
*/	
	@Override public void updateSection(SECTION section)
	{
		if(!mapQuestion.containsKey(section)){mapQuestion.put(section,new ArrayList<QUESTION>());}
		mapQuestion.get(section).clear();
		mapQuestion.get(section).addAll(section.getQuestions());
	}
	
	@Override public void updateOptions(QUESTION question)
	{
		if(!mapOption.containsKey(question)){mapOption.put(question,new ArrayList<OPTION>());}
		mapOption.get(question).clear();
		
		if(question.getOptionSet()==null)
		{
			for(OPTION o : question.getOptions()){mapOptionId.put(o.getId(),o);}
			mapOption.get(question).addAll(question.getOptions());
			matrixRows.put(question, efOption.toRows(question.getOptions()));
			matrixCols.put(question, efOption.toColumns(question.getOptions()));
			matrixCells.put(question, efOption.toCells(question.getOptions()));
		}
		else
		{
			mapOption.get(question).addAll(mapOptionSet.get(question.getOptionSet()));
			matrixRows.put(question, efOption.toRows(mapOptionSet.get(question.getOptionSet())));
			matrixCols.put(question, efOption.toColumns(mapOptionSet.get(question.getOptionSet())));
			matrixCells.put(question, efOption.toCells(mapOptionSet.get(question.getOptionSet())));
		}
	}
	
	@Override public void updateOptions(OPTIONS set)
	{
		for(OPTION o : set.getOptions()){mapOptionId.put(o.getId(),o);}
		if(!mapOptionSet.containsKey(set)){mapOptionSet.put(set,new ArrayList<OPTION>());}
		mapOptionSet.get(set).clear();
		mapOptionSet.get(set).addAll(set.getOptions());
		for(QUESTION question : fSurvey.allForParent(ffSurvey.getClassQuestion(), JeeslSurveyQuestion.Attributes.optionSet.toString(), set))
		{
			question = fSurvey.load(question);
			updateOptions(question);
		}
	}
	
	protected String statistics()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("mapSection: ").append(mapSection.size());
		sb.append(" mapOptionId: ").append(mapOptionId.size());
		return sb.toString();
	}
}