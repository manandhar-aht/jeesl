package org.jeesl.controller.module;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyNativeQueryBuilder <SURVEY extends JeeslSurvey<?,?,?,?,?>,
									  QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyNativeQueryBuilder.class);

	public SurveyNativeQueryBuilder()
	{
		
	}
	
	public String build(SURVEY survey)
	{
		String sql = "select answer.question_id as questionId, answer.option_id as optionId, correlation.id as correlationId, count(answer.option_id) as counter\n" + 
				"from SurveyAnswer answer\n" + 
				"                inner join SurveyData data on answer.data_id=data.id\n" + 
				"                inner join Survey survey on data.survey_id=survey.id\n" + 
				"                inner join SurveyCorrelation correlation on data.correlation_id=correlation.id\n" + 
				"                inner join SurveyCorrelationErpUser corruser on correlation.id=corruser.id\n" + 
				"                inner join User user on user.id=corruser.user_id\n" + 
				"                inner join Cv cv on cv.user_id=corruser.user_id,\n" + 
				"                SurveyOption opt\n" + 
				"where answer.option_id=opt.id and (survey.id in (2)) and answer.question_id=34\n" + 
				"group by answer.question_id , answer.option_id , cv.gender_id";
		
		return sql;
	}
}