package org.jeesl.factory.builder.survey;

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
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveyAnalysisFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
				TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,OPTIONS,ANALYSIS>,
				QUESTION extends JeeslSurveyQuestion<L,D,?,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,?,QUESTION>,
				UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
				DATA extends JeeslSurveyData<L,D,?,ANSWER,CORRELATION>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
				OPTION extends JeeslSurveyOption<L,D>,
				CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
				DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
				AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
			extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyAnalysisFactoryBuilder.class);
	
	private final Class<AQ> cAq; public Class<AQ> getClassAnalysisQuestion() {return cAq;}
	private final Class<ATT> cAtt; public Class<ATT> getAttClass() {return cAtt;}

	public SurveyAnalysisFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<AQ> cAq, final Class<ATT> cAtt)
	{
		super(cL,cD);
		this.cAq=cAq;
        this.cAtt = cAtt;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,OPTIONS,ANALYSIS>,
					QUESTION extends JeeslSurveyQuestion<L,D,?,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
					QE extends UtilsStatus<QE,L,D>,
					SCORE extends JeeslSurveyScore<L,D,?,QUESTION>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
					MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
					DATA extends JeeslSurveyData<L,D,?,ANSWER,CORRELATION>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
					DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>,
					AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
					AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
					ATT extends UtilsStatus<ATT,L,D>>
		SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
		factory(final Class<L> cL, final Class<D> cD, final Class<AQ> cAq, final Class<ATT> cAtt)
	{
		return new SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(cL,cD,cAq,cAtt);
	}
		
	public EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY> ejbDomain(Class<DOMAIN> cDomain)
	{
		return new EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY>(cDomain);
	}
	
	public EjbSurveyAnalysisFactory<TEMPLATE,ANALYSIS> ejbAnalysis(Class<ANALYSIS> cAnalysis)
	{
		return new EjbSurveyAnalysisFactory<TEMPLATE,ANALYSIS>(cAnalysis);
	}
	
	public EjbSurveyAnalysisQuestionFactory<L,D,QUESTION,ANALYSIS,AQ> ejbAnalysisQuestion(Class<AQ> cAQ)
	{
		return new EjbSurveyAnalysisQuestionFactory<L,D,QUESTION,ANALYSIS,AQ>(cAQ);
	}
	
	public EjbSurveyAnalysisToolFactory<L,D,AQ,AT,ATT> ejbAnalysisTool(Class<AT> cAt)
	{
		return new EjbSurveyAnalysisToolFactory<L,D,AQ,AT,ATT>(cAt);
	}
}