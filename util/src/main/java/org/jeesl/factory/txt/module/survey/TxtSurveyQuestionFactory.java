package org.jeesl.factory.txt.module.survey;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
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
import org.jeesl.interfaces.model.system.io.domain.JeeslSurveyDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtSurveyQuestionFactory <L extends UtilsLang, D extends UtilsDescription,
										QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,OPTION,?>,
										OPTION extends JeeslSurveyOption<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSurveyQuestionFactory.class);
		
	private DecimalFormat df = new DecimalFormat("0.#");
	
	public Map<QUESTION,String> scoreBoundsQuestions(Set<QUESTION> set)
	{
		List<QUESTION> questions = new ArrayList<QUESTION>(set);
		return scoreBoundsQuestions(questions);
	}
	
	public Map<QUESTION,String> scoreBoundsQuestions(List<QUESTION> list)
	{
		Map<QUESTION,String> map = new HashMap<QUESTION,String>();
		for(QUESTION q : list)
		{
			map.put(q,scoreBounds(q));
		}
		return map;
	}
	
	public String scoreBounds(QUESTION q)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(df.format(q.getMinScore()));
		sb.append(",");
		sb.append(df.format(q.getMaxScore()));
		sb.append("]");
		return sb.toString();
	}
}