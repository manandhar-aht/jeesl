package org.jeesl.factory.txt.module.survey;

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

public class TxtSurveyAnswerFactory<L extends UtilsLang, D extends UtilsDescription,
									ANSWER extends JeeslSurveyAnswer<L,D,?,MATRIX,?,OPTION>,
									MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
									OPTION extends JeeslSurveyOption<L,D>
									>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSurveyAnswerFactory.class);
	
	public String build(ANSWER answer)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("[").append(answer.getId()).append("]");
		
		if(answer.getValueBoolean()!=null){sb.append(" boolean:").append(answer.getValueBoolean());}
		if(answer.getValueNumber()!=null){sb.append(" number:").append(answer.getValueNumber());}
		
		return sb.toString();
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					
					ANSWER extends JeeslSurveyAnswer<L,D,?,?,?,?>
					>
		String debug(ANSWER answer)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("[").append(answer.getId()).append("]");
		
		if(answer.getValueBoolean()!=null){sb.append(" boolean:").append(answer.getValueBoolean());}
		if(answer.getValueNumber()!=null){sb.append(" number:").append(answer.getValueNumber());}
		
		return sb.toString();
	}
}