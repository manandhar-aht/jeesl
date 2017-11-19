package org.jeesl.factory.builder.module.survey;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisToolFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainPathFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainQueryFactory;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveyAnalysisFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
				TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,ANALYSIS>,
				QUESTION extends JeeslSurveyQuestion<L,D,?,QE,SCORE,?,?,OPTION,AQ>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,?,QUESTION>,
				ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
				DATA extends JeeslSurveyData<L,D,?,ANSWER,CORRELATION>,
				OPTION extends JeeslSurveyOption<L,D>,
				CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
				DOMAIN extends JeeslSurveyDomain<L,D,DENTITY>,
				QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN>,
				PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
				DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
				AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
			extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyAnalysisFactoryBuilder.class);
	
	private final Class<ANSWER> cAnswer; public Class<ANSWER> getClassAnswer() {return cAnswer;}
	private final Class<DATA> cData; public Class<DATA> getClassData() {return cData;}
	private final Class<DOMAIN> cDomain; public Class<DOMAIN> getClassDomain() {return cDomain;}
	private final Class<QUERY> cDomainQuery; public Class<QUERY> getClassDomainQuery() {return cDomainQuery;}
	private final Class<PATH> cDomainPath; public Class<PATH> getClassDomainPath() {return cDomainPath;}
	private final Class<DENTITY> cDomainEntity; public Class<DENTITY> getClassDomainEntity() {return cDomainEntity;}
	private final Class<DATTRIBUTE> cDomainAttribute; public Class<DATTRIBUTE> getClassDomainAttribute() {return cDomainAttribute;}
	private final Class<ANALYSIS> cAnalysis; public Class<ANALYSIS> getClassAnalysis() {return cAnalysis;}
	private final Class<AQ> cAq; public Class<AQ> getClassAnalysisQuestion() {return cAq;}
	private final Class<AT> cTool; public Class<AT> getClassAnalysisTool() {return cTool;}
	private final Class<ATT> cAtt; public Class<ATT> getAttClass() {return cAtt;}

	public SurveyAnalysisFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<ANSWER> cAnswer, final Class<DATA> cData, final Class<DOMAIN> cDomain, final Class<QUERY> cDomainQuery, final Class<PATH> cDomainPath, final Class<DENTITY> cDomainEntity, final Class<DATTRIBUTE> cDomainAttribute, final Class<ANALYSIS> cAnalysis, final Class<AQ> cAq, final Class<AT> cTool, final Class<ATT> cAtt)
	{
		super(cL,cD);
		this.cAnswer=cAnswer;
		this.cData=cData;
		this.cDomain=cDomain;
		this.cDomainQuery=cDomainQuery;
		this.cDomainPath=cDomainPath;
		this.cDomainEntity=cDomainEntity;
		this.cDomainAttribute=cDomainAttribute;
		this.cAq=cAq;
		this.cAnalysis=cAnalysis;
		this.cTool=cTool;
        this.cAtt = cAtt;
	}
	
	public EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY> ejbDomain()
	{
		return new EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY>(cDomain);
	}
	
	public EjbSurveyDomainQueryFactory<L,D,DOMAIN,QUERY> ejbDomainQuery()
	{
		return new EjbSurveyDomainQueryFactory<L,D,DOMAIN,QUERY>(cDomainQuery);
	}
	
	public EjbSurveyDomainPathFactory<L,D,QUERY,PATH,DENTITY,DATTRIBUTE> ejbDomainPath()
	{
		return new EjbSurveyDomainPathFactory<L,D,QUERY,PATH,DENTITY,DATTRIBUTE>(cDomainPath);
	}
	
	public EjbSurveyAnalysisFactory<TEMPLATE,ANALYSIS> ejbAnalysis()
	{
		return new EjbSurveyAnalysisFactory<TEMPLATE,ANALYSIS>(cAnalysis);
	}
	
	public EjbSurveyAnalysisQuestionFactory<L,D,QUESTION,ANALYSIS,AQ> ejbAnalysisQuestion()
	{
		return new EjbSurveyAnalysisQuestionFactory<L,D,QUESTION,ANALYSIS,AQ>(cAq);
	}
	
	public EjbSurveyAnalysisToolFactory<L,D,AQ,AT,ATT> ejbAnalysisTool()
	{
		return new EjbSurveyAnalysisToolFactory<L,D,AQ,AT,ATT>(cTool);
	}
}