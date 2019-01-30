package org.jeesl.controller.processor.survey.analysis;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.controller.handler.module.SurveyAnalysisCacheHandler;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisToolFactory;
import org.jeesl.factory.mc.survey.McOptionDataSetFactory;
import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;
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
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidationAlgorithm;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveySelectOneProcessor<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
										SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
										SS extends UtilsStatus<SS,L,D>,
										SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
										VALGORITHM extends JeeslSurveyValidationAlgorithm<L,D>,
										TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,
										VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
										QUESTION extends JeeslSurveyQuestion<L,D,SECTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
										CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
										VALIDATION extends JeeslSurveyValidation<QUESTION,VALGORITHM>,
										QE extends UtilsStatus<QE,L,D>,
										SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
										MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
										DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
										OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
										OPTION extends JeeslSurveyOption<L,D>,
										CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
										DOMAIN extends JeeslDomain<L,DENTITY>,
										QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
										PATH extends JeeslDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
										DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
										DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
										ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN,DENTITY,DATTRIBUTE>,
										AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
										TOOL extends JeeslSurveyAnalysisTool<L,D,QE,QUERY,DATTRIBUTE,AQ,ATT>,
										ATT extends UtilsStatus<ATT,L,D>,
										TOOLCACHETEMPLATE extends JeeslJobTemplate<L,D,?,?,?>,
										CACHE extends JeeslJobCache<TOOLCACHETEMPLATE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(SurveySelectOneProcessor.class);
	
	protected boolean debugOnInfo; protected void setDebugOnInfo(boolean debugOnInfo){this.debugOnInfo=debugOnInfo;}
	
	JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey;
	
	protected final EjbSurveyAnalysisToolFactory<L,D,AQ,TOOL,ATT> efTool;
	JeeslReportAggregationLevelFactory tfName;
	McOptionDataSetFactory<OPTION> mfOption;
	
	SurveyAnalysisCacheHandler<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,TOOL,ATT,TOOLCACHETEMPLATE,CACHE> cacheHandler;
	
	public SurveySelectOneProcessor(
					JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey,
					SurveyAnalysisCacheHandler<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,TOOL,ATT,TOOLCACHETEMPLATE,CACHE> ch,
					EjbSurveyAnalysisToolFactory<L,D,AQ,TOOL,ATT> efTool,
					JeeslReportAggregationLevelFactory tfName)
	{
		this.efTool=efTool;
		
		mfOption = new McOptionDataSetFactory<OPTION>(tfName);
	}
	
	public void build(SURVEY survey, QUESTION q, TOOL tool)
	{

//COPY FROM AbstractSurveyReport
/*
		if(debugOnInfo) {logger.info("Handling :"+JeeslSurveyAnalysisTool.Elements.selectOne+" withDomainQuery:"+efTool.withDomainQuery(tool));}
		JsonFlatFigures ff = cacheHandler.surveyStatisticOption(q, survey, tool);
		
		for(JsonFlatFigure f : ff.getFigures())
		{
			optionIds.add(f.getL2());
		}
		mapToolTableOptionGlobal.put(tool,ff);
		
		if(!efTool.withDomainQuery(tool))
		{
			DataSet ds2 = mfOption.build(ff,bSurvey.getMapOption().get(q));
			mapDsOption.put(q,ds2);
		}
		else
		{
			Nested2Map<JsonSurveyValue,JsonSurveyValue,JsonSurveyValue> nm2 = new Nested2Map<JsonSurveyValue,JsonSurveyValue,JsonSurveyValue>();
			long idValue = 1;
			Set<Long> pathIds = new HashSet<Long>();
			for(JsonFlatFigure f : ff.getFigures())
			{
				idValue++;
				JsonSurveyValue vOption = JsonSurveyValueFactory.build(f.getL2());
				JsonSurveyValue vPath = JsonSurveyValueFactory.build(f.getL4());
				JsonSurveyValue vCount = JsonSurveyValueFactory.build(idValue,f.getL3());
				nm2.put(vOption, vPath, vCount);
				pathIds.add(vPath.getId());
			}
			mapToolPathValue.put(tool,nm2);
			buildPathEntities(tool,pathIds);
		}
		
		mapToolOption.put(tool,JsonSurveyValueFactory.build(optionIds));
*/
	}
}