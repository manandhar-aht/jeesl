package org.jeesl.api.bean;

import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.module.survey.JeeslSurveyCache;
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

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSurveyBean<L extends UtilsLang, D extends UtilsDescription,
					SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
					SS extends UtilsStatus<SS,L,D>,
					SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
					TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
					TS extends UtilsStatus<TS,L,D>,
					TC extends UtilsStatus<TC,L,D>,
					SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
					QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
					CONDITION extends JeeslSurveyCondition<QUESTION,?,OPTION>,
					QE extends UtilsStatus<QE,L,D>,
					SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
					MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
					DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
					ATT extends UtilsStatus<ATT,L,D>>
				extends JeeslSurveyCache<TEMPLATE,SECTION,QUESTION,CONDITION>
{	
	List<ATT> getToolTypes();
	List<QE> getElements();
	
	Map<TEMPLATE,List<SECTION>> getMapSection();
	Map<SECTION,List<QUESTION>> getMapQuestion();
	
	Map<Long,OPTION> getMapOptionId();
	Map<QUESTION,List<OPTION>> getMapOption();
	Map<QUESTION,List<OPTION>> getMatrixRows();
	Map<QUESTION,List<OPTION>> getMatrixCols();
		
	void updateTemplate(TEMPLATE template);
	void updateSection(SECTION section);
	void updateOptions(QUESTION question);
	void updateOptions(OPTIONS set);
}