package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionFactory;
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
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractAppSurveyBean <L extends UtilsLang, D extends UtilsDescription,
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
					implements Serializable,
								JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppSurveyBean.class);

	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey;
	private final SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> ffSurvey;
		
	protected final EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> efOption;
	
	protected Map<TEMPLATE,List<SECTION>> mapSection; @Override public Map<TEMPLATE,List<SECTION>> getMapSection() {return mapSection;}
	protected Map<SECTION,List<QUESTION>> mapQuestion; @Override public Map<SECTION,List<QUESTION>> getMapQuestion() {return mapQuestion;}

	protected Map<Long,OPTION> mapOptionId; @Override public Map<Long,OPTION> getMapOptionId(){return mapOptionId;}
	protected Map<QUESTION,List<OPTION>> mapOption; @Override public Map<QUESTION,List<OPTION>> getMapOption() {return mapOption;}

	protected Map<QUESTION,List<OPTION>> matrixRows; @Override public Map<QUESTION,List<OPTION>> getMatrixRows() {return matrixRows;}
	protected Map<QUESTION,List<OPTION>> matrixCols; @Override public Map<QUESTION,List<OPTION>> getMatrixCols() {return matrixCols;}
	protected Map<QUESTION,List<OPTION>> matrixCells; public Map<QUESTION,List<OPTION>> getMatrixCells() {return matrixCells;}


	
	public AbstractAppSurveyBean(SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> ffSurvey)
	{
		this.ffSurvey=ffSurvey;
		efOption = ffSurvey.option();
		
		mapSection = new HashMap<TEMPLATE,List<SECTION>>();
		mapQuestion = new HashMap<SECTION,List<QUESTION>>();
		
		mapOptionId = new HashMap<Long,OPTION>();
		mapOption = new HashMap<QUESTION,List<OPTION>>();
		matrixRows = new HashMap<QUESTION,List<OPTION>>();
		
		matrixCols = new HashMap<QUESTION,List<OPTION>>();
		matrixCells = new HashMap<QUESTION,List<OPTION>>();
	}
	
	public void initSuper(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey)
	{
		this.fSurvey=fSurvey;
		refreshUnits();
		refreshSurveyStatus();
		
		//Cache
		reloadSections();
		reloadQuestions();
		reloadOptions();
	}
	
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
	
	private void reloadSections()
	{
		mapSection.clear();
		for(SECTION s : fSurvey.allOrderedPosition(ffSurvey.getClassSection()))
		{
			if(!mapSection.containsKey(s.getTemplate())){mapSection.put(s.getTemplate(),new ArrayList<SECTION>());}
			if(s.isVisible())
			{
				mapSection.get(s.getTemplate()).add(s);
			}
		}
	}
	
	private void reloadQuestions()
	{
		mapQuestion.clear();
		for(QUESTION q : fSurvey.allOrderedPosition(ffSurvey.getClassQuestion()))
		{
			if(!mapQuestion.containsKey(q.getSection())){mapQuestion.put(q.getSection(),new ArrayList<QUESTION>());}
			mapQuestion.get(q.getSection()).add(q);
		}
	}
	
	public void reloadOptions()
	{
		mapOption.clear();
		for(OPTION o : fSurvey.allOrderedPosition(ffSurvey.getOptionClass()))
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
	}
	
	@Override public void updateSection(SECTION section)
	{
		if(!mapQuestion.containsKey(section)){mapQuestion.put(section,new ArrayList<QUESTION>());}
		mapQuestion.get(section).clear();
		mapQuestion.get(section).addAll(section.getQuestions());
	}
	
	@Override public void updateOptions(QUESTION question)
	{
		for(OPTION o : question.getOptions()){mapOptionId.put(o.getId(),o);}
		if(!mapOption.containsKey(question)){mapOption.put(question,new ArrayList<OPTION>());}
		mapOption.get(question).clear();
		mapOption.get(question).addAll(question.getOptions());
		
		matrixRows.put(question, efOption.toRows(question.getOptions()));
		matrixCols.put(question, efOption.toColumns(question.getOptions()));
		matrixCells.put(question, efOption.toCells(question.getOptions()));
	}
	
	protected String statistics()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Sections: ").append(mapSection.size());
		return sb.toString();
	}
}