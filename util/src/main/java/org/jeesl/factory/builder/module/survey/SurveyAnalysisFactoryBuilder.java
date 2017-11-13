package org.jeesl.factory.builder.module.survey;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisToolFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainFactory;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
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
				DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
				AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
			extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyAnalysisFactoryBuilder.class);
	
	private final Class<ANSWER> cAnswer; public Class<ANSWER> getClassAnswer() {return cAnswer;}
	private final Class<DATA> cData; public Class<DATA> getClassData() {return cData;}
	private final Class<ANALYSIS> cAnalysis; public Class<ANALYSIS> getClassAnalysis() {return cAnalysis;}
	private final Class<AQ> cAq; public Class<AQ> getClassAnalysisQuestion() {return cAq;}
	private final Class<AT> cTool; public Class<AT> getClassAnalysisTool() {return cTool;}
	private final Class<ATT> cAtt; public Class<ATT> getAttClass() {return cAtt;}

	public SurveyAnalysisFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<ANSWER> cAnswer, final Class<DATA> cData, final Class<ANALYSIS> cAnalysis, final Class<AQ> cAq, final Class<AT> cTool, final Class<ATT> cAtt)
	{
		super(cL,cD);
		this.cAnswer=cAnswer;
		this.cData=cData;
		this.cAq=cAq;
		this.cAnalysis=cAnalysis;
		this.cTool=cTool;
        this.cAtt = cAtt;
	}
	
	public EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY> ejbDomain(Class<DOMAIN> cDomain)
	{
		return new EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY>(cDomain);
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