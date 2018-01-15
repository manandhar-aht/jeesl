package org.jeesl.factory.sql.module.survey;

import java.util.HashMap;
import java.util.Map;

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
import org.jeesl.util.ReflectionUtil;
import org.jeesl.util.comparator.pojo.BooleanComparator;
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
									  TOOL extends JeeslSurveyAnalysisTool<?,?,?,QUERY,DATTRIBUTE,?,?>>
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
	
	private String getTableName(String c)
	{
		if(!mapTable.containsKey(c))
		{
			try
			{
				Class<?> cl = Class.forName(c);
				mapTable.put(cl.getName(), ReflectionUtil.toTable(cl));
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
		}
		return mapTable.get(c);
	}
	
	public String build(QUESTION question, SURVEY survey, TOOL tool)
	{
		String tbAnswer = getTableName(fbCore.getClassAnswer().getName());
		String tbData = getTableName(fbCore.getClassData().getName());
		String tbSurvey = getTableName(fbCore.getClassSurvey().getName());
		String tbCorrelation = getTableName(fbCore.getClassCorrelation().getName());
		
		StringBuilder sbCorelationColumn = new StringBuilder();
		StringBuilder sbCorelationJoin = new StringBuilder();
		
		if(tool!=null && tool.getQuery()!=null)
		{
			sbCorelationColumn.append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(".id as correlationId");
			
			sbCorelationJoin.append("INNER JOIN ").append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(" ON ").append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(".id=").append(tbCorrelation).append(".id\n");
			
			for(int i=0;i<tool.getQuery().getPaths().size();i++)
			{
				PATH p = tool.getQuery().getPaths().get(i);
				sbCorelationJoin.append("    INNER JOIN ").append(getTableName(p.getEntity().getCode())).append(" ON ");
				
				if(i==0)
				{
					sbCorelationJoin.append(getTableName(p.getEntity().getCode())).append(".id");
					sbCorelationJoin.append("=");
					sbCorelationJoin.append(getTableName(tool.getAnalysisQuestion().getAnalysis().getEntity().getCode())).append(".");
					sbCorelationJoin.append(tool.getAttribute().getCode()).append("_id");
					sbCorelationJoin.append("\n");
				}
				else
				{
					PATH previousPath = tool.getQuery().getPaths().get(i-1);
					
					if(BooleanComparator.active(previousPath.getAttribute().getRelationOwner()))
					{
						sbCorelationJoin.append(getTableName(p.getEntity().getCode())).append(".id");
						sbCorelationJoin.append("=");
						sbCorelationJoin.append(getTableName(previousPath.getEntity().getCode())).append(".");
						sbCorelationJoin.append(previousPath.getAttribute().getCode()).append("_id\n");
					}
					else
					{
						sbCorelationJoin.append(getTableName(p.getEntity().getCode())).append(".");
						try
						{
							sbCorelationJoin.append(ReflectionUtil.getReverseMapping(previousPath.getEntity().getCode(), "", p.getEntity().getCode()));
							sbCorelationJoin.append("_id");
						}
						catch (UtilsNotFoundException e) {e.printStackTrace();}
						sbCorelationJoin.append("=");
						sbCorelationJoin.append(getTableName(previousPath.getEntity().getCode())).append(".id\n");
					}
				}
				sbCorelationColumn.setLength(0);
				sbCorelationColumn.append(getTableName(p.getEntity().getCode())).append(".").append(p.getAttribute().getCode()).append("_id");
			}
		}
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ").append(tbAnswer).append(".question_id as questionId");
		sb.append(", ").append(tbAnswer).append(".option_id as optionId");
		sb.append(", COUNT(").append(tbAnswer).append(".option_id) as counter");
		if(sbCorelationColumn.length()>0) {sb.append(", ").append(sbCorelationColumn);}
		sb.append("\n");
		
		sb.append("FROM ").append(getTableName(fbCore.getClassAnswer().getName())).append("\n");
		sb.append("INNER JOIN ").append(tbData).append(" ON ").append(tbData).append(".id=").append(getTableName(fbCore.getClassAnswer().getName())).append(".data_id\n");
		sb.append("INNER JOIN ").append(tbSurvey).append(" ON ").append(tbSurvey).append(".id=").append(tbData).append(".survey_id\n");
		
		sb.append("INNER JOIN ").append(tbCorrelation).append(" ON ").append(tbCorrelation).append(".id=").append(tbData).append(".correlation_id\n");
		
		sb.append(sbCorelationJoin);
		sb.append("  ,SurveyOption opt\n");
		
		sb.append("WHERE ").append(tbAnswer).append(".option_id=opt.id\n");
		sb.append(" AND (").append(tbSurvey).append(".id in (").append(survey.getId()).append("))\n");
		sb.append(" AND ").append(tbAnswer).append(".question_id=").append(question.getId()).append("\n");
		sb.append("GROUP BY ").append(tbAnswer).append(".question_id, ");
							  sb.append(tbAnswer).append(".option_id");
		if(sbCorelationColumn.length()>0)
		{
			sb.append(", ").append(sbCorelationColumn);
		}
		return sb.toString();
	}
}