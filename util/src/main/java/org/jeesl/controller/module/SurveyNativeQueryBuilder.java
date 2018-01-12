package org.jeesl.controller.module;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class SurveyNativeQueryBuilder <SURVEY extends JeeslSurvey<?,?,?,?,DATA>,
									  QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?>,
									  ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,DATA,?>,
									  DATA extends JeeslSurveyData<?,?,SURVEY,ANSWER,CORRELATION>,
									  CORRELATION extends JeeslSurveyCorrelation<?,?,DATA>,
									  TOOL extends JeeslSurveyAnalysisTool<?,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyNativeQueryBuilder.class);

	private final Map<String,String> mapTable;
	
	private final SurveyCoreFactoryBuilder<?,?,SURVEY,?,?,?,?,?,?,?,QUESTION,?,?,?,ANSWER,?,DATA,?,?,CORRELATION,?> fbCore;
	private final SurveyAnalysisFactoryBuilder<?,?,?,QUESTION,?,?,ANSWER,?,DATA,?,CORRELATION,?,?,?,?,?,?,?,TOOL,?> fbAnalysis;
	
	public SurveyNativeQueryBuilder(SurveyCoreFactoryBuilder<?,?,SURVEY,?,?,?,?,?,?,?,QUESTION,?,?,?,ANSWER,?,DATA,?,?,CORRELATION,?> fbCore,
									SurveyAnalysisFactoryBuilder<?,?,?,QUESTION,?,?,ANSWER,?,DATA,?,CORRELATION,?,?,?,?,?,?,?,TOOL,?> fbAnalysis)
	{
		this.fbCore=fbCore;
		this.fbAnalysis=fbAnalysis;
		
		mapTable = new HashMap<String,String>();
	}
	
	private String createNode(Class<?> c) throws UtilsNotFoundException
	{
		Annotation a = c.getAnnotation(Table.class);
		if(a!=null)
		{
			Table t = (Table)a;
			return t.name();
	
		}
		return "--";
	}
	
	private void prepareTableNames() throws UtilsNotFoundException
	{
		mapTable.put(fbCore.getClassAnswer().getName(), createNode(fbCore.getClassAnswer()));
	}
	
	private String getTableName(String c)
	{
		return mapTable.get(c);
	}
	
	public String build(SURVEY survey)
	{
		if(mapTable.isEmpty())
		{
			try {
				prepareTableNames();
			} catch (UtilsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
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