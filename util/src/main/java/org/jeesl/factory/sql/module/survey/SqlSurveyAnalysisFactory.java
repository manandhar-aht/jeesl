package org.jeesl.factory.sql.module.survey;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class SqlSurveyAnalysisFactory <SURVEY extends JeeslSurvey<?,?,?,?,DATA>,
									  QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?>,
									  ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,DATA,?>,
									  DATA extends JeeslSurveyData<?,?,SURVEY,ANSWER,CORRELATION>,
									  CORRELATION extends JeeslSurveyCorrelation<?,?,DATA>,
									  DOMAIN extends JeeslSurveyDomain<?,DENTITY>,
									  QUERY extends JeeslSurveyDomainQuery<?,?,DOMAIN,PATH>,
									  PATH extends JeeslSurveyDomainPath<?,?,QUERY,DENTITY,DATTRIBUTE>,
									  DENTITY extends JeeslRevisionEntity<?,?,?,?,DATTRIBUTE>,
									  DATTRIBUTE extends JeeslRevisionAttribute<?,?,DENTITY,?,?>,
									  ANALYSIS extends JeeslSurveyAnalysis<?,?,?,DOMAIN,DENTITY,DATTRIBUTE>,
									  AQ extends JeeslSurveyAnalysisQuestion<?,?,QUESTION,ANALYSIS>,
									  TOOL extends JeeslSurveyAnalysisTool<?,?,?,QUERY,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(SqlSurveyAnalysisFactory.class);

	private final Map<String,String> mapTable;
	
	private final SurveyCoreFactoryBuilder<?,?,SURVEY,?,?,?,?,?,?,?,QUESTION,?,?,?,ANSWER,?,DATA,?,?,CORRELATION,?> fbCore;
	private final SurveyAnalysisFactoryBuilder<?,?,?,QUESTION,?,?,ANSWER,?,DATA,?,CORRELATION,?,?,?,?,?,?,?,TOOL,?> fbAnalysis;
	
	public SqlSurveyAnalysisFactory(SurveyCoreFactoryBuilder<?,?,SURVEY,?,?,?,?,?,?,?,QUESTION,?,?,?,ANSWER,?,DATA,?,?,CORRELATION,?> fbCore,
									SurveyAnalysisFactoryBuilder<?,?,?,QUESTION,?,?,ANSWER,?,DATA,?,CORRELATION,DOMAIN,?,?,?,?,?,?,TOOL,?> fbAnalysis)
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
		mapTable.put(fbCore.getClassSurvey().getName(), createNode(fbCore.getClassSurvey()));
		mapTable.put(fbCore.getClassData().getName(), createNode(fbCore.getClassData()));
	}
	
	private String getTableName(String c)
	{
		if(!mapTable.containsKey(c))
		{
			try
			{
				Class<?> cl = Class.forName(c);
				mapTable.put(cl.getName(), createNode(cl));
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
		}
		return mapTable.get(c);
	}
	
	public String build(SURVEY survey, TOOL tool)
	{
		StringBuilder sbCorelationColumn = new StringBuilder();
		StringBuilder sbCorelationJoin = new StringBuilder();
		
		if(tool!=null && tool.getQuery()!=null)
		{
			sbCorelationJoin.append("INNER JOIN ").append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(" ON correlation.id=").append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(".id\n");
			sbCorelationColumn.append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(".id as correlationId");
			
//			for(PATH p : tool.getQuery().get)
		}
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("select answer.question_id as questionId, answer.option_id as optionId");
		
		if(sbCorelationColumn.length()>0)
		{
			sb.append(", ").append(sbCorelationColumn);
		}
		sb.append(", count(answer.option_id) as counter\n");
		sb.append("FROM ").append(getTableName(fbCore.getClassAnswer().getName())).append(" as answer\n");
		sb.append("INNER JOIN ").append(getTableName(fbCore.getClassData().getName())).append(" data on answer.data_id=data.id\n");
		sb.append("INNER JOIN ").append(getTableName(fbCore.getClassSurvey().getName())).append(" survey on data.survey_id=survey.id\n");
		sb.append("INNER JOIN ").append(getTableName(fbCore.getClassCorrelation().getName())).append(" correlation on data.correlation_id=correlation.id\n");
		
		sb.append(sbCorelationJoin);
		
		String sql = 

//				"                inner join User user on user.id=corruser.user_id\n" + 
//				"                inner join Cv cv on cv.user_id=corruser.user_id,\n" + 
				"                ,SurveyOption opt\n" + 
				"where answer.option_id=opt.id and (survey.id in (2)) and answer.question_id=34\n" + 
				"group by answer.question_id , answer.option_id , correlation.id";
		
//		tool.getQuery().getDomain().getEntity().getCode();
		
		return sb.toString()+sql;
	}
}