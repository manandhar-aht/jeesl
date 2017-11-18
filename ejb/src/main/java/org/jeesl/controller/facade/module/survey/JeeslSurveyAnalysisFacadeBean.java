package org.jeesl.controller.facade.module.survey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.survey.JeeslSurveyAnalysisFacade;
import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.json.system.io.report.JsonFlatFigureFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFiguresFactory;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.model.json.JsonFlatFigure;
import org.jeesl.model.json.JsonFlatFigures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSurveyAnalysisFacadeBean <L extends UtilsLang, D extends UtilsDescription,
				SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,?,?,SECTION,OPTIONS,ANALYSIS>,
				VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
				SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
				UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
				DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
				OPTION extends JeeslSurveyOption<L,D>,
				CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
				DOMAIN extends JeeslSurveyDomain<L,D,DENTITY>,
				QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN>,
				PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,?>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
				AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
	extends UtilsFacadeBean implements JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslSurveyAnalysisFacadeBean.class);
	
	private final SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fbAnalyis;
	
	public JeeslSurveyAnalysisFacadeBean(EntityManager em, final SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fbAnalyis)
	{
		super(em);
		this.fbAnalyis=fbAnalyis;
	}
	
	@Override public AQ fAnalysis(ANALYSIS analysis, QUESTION question) throws UtilsNotFoundException
	{
		return this.oneForParents(fbAnalyis.getClassAnalysisQuestion(), JeeslSurveyAnalysisQuestion.Attributes.analysis.toString(), analysis, JeeslSurveyAnalysisQuestion.Attributes.question.toString(), question);
	}
	
	@Override public JsonFlatFigures surveyCountRecords(List<SURVEY> surveys)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
        Root<DATA> data = cQ.from(fbAnalyis.getClassData());
        
        Path<SURVEY> pSurvey = data.get(JeeslSurveyData.Attributes.survey.toString());
        predicates.add(pSurvey.in(surveys));
        
        Expression<Long> eTa = cB.count(data.<Long>get("id"));
      
        cQ.groupBy(pSurvey.get("id"));
        cQ.multiselect(pSurvey.get("id"),eTa);
        
        cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Tuple> tQ = em.createQuery(cQ);
        List<Tuple> tuples = tQ.getResultList();
        
        JsonFlatFigures result = JsonFlatFiguresFactory.build();
        for(Tuple t : tuples)
        {
	        	JsonFlatFigure f = JsonFlatFigureFactory.build();
	        	f.setL1((Long)t.get(0));
	        	f.setL2((Long)t.get(1));
	        	result.getFigures().add(f);
        }
        
        return result;
	}
	
	@Override public JsonFlatFigures surveyCountAnswer(List<QUESTION> questions, SURVEY survey, List<CORRELATION> correlations)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
        
        Root<ANSWER> answer = cQ.from(fbAnalyis.getClassAnswer());
        
        Path<QUESTION> pQuestion = answer.get(JeeslSurveyAnswer.Attributes.question.toString());
     
        Expression<Long> eTa = cB.count(answer.<Long>get("id"));
      
        cQ.groupBy(pQuestion.get("id"));
        cQ.multiselect(pQuestion.get("id"),eTa);
        cQ.where(pQuestion.in(questions));
        
        TypedQuery<Tuple> tQ = em.createQuery(cQ);
        List<Tuple> tuples = tQ.getResultList();
        
        JsonFlatFigures result = JsonFlatFiguresFactory.build();
        for(Tuple t : tuples)
        {
	        	JsonFlatFigure f = JsonFlatFigureFactory.build();
	        	f.setL1((Long)t.get(0));
	        	f.setL2((Long)t.get(1));
	        	result.getFigures().add(f);
        }
        
        return result;
	}
	
	@Override public JsonFlatFigures surveyStatisticOption(QUESTION question, SURVEY survey)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
        
        Root<ANSWER> answer = cQ.from(fbAnalyis.getClassAnswer());
        Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
        Join<DATA,SURVEY> jSurvey = jData.join(JeeslSurveyData.Attributes.survey.toString());
        predicates.add(jSurvey.in(survey));
        
        Path<QUESTION> pQuestion = answer.get(JeeslSurveyAnswer.Attributes.question.toString());
        predicates.add(cB.equal(pQuestion,question));
        
        Path<OPTION> pOption = answer.get(JeeslSurveyAnswer.Attributes.option.toString());
        
        Expression<Long> eTa = cB.count(answer.<Long>get(JeeslSurveyAnswer.Attributes.option.toString()));
      
        cQ.groupBy(pQuestion.get("id"),pOption.get("id"));
        cQ.multiselect(pQuestion.get("id"),pOption.get("id"),eTa);

        cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Tuple> tQ = em.createQuery(cQ);
        List<Tuple> tuples = tQ.getResultList();
        
        JsonFlatFigures result = JsonFlatFiguresFactory.build();
        for(Tuple t : tuples)
        {
	        	JsonFlatFigure f = JsonFlatFigureFactory.build();
	        	f.setL1((Long)t.get(0));
	        	f.setL2((Long)t.get(1));
	        	f.setL3((Long)t.get(2));
	        	result.getFigures().add(f);
        }
        
        return result;
	}
	
	@Override public JsonFlatFigures surveyStatisticBoolean(QUESTION question, SURVEY survey)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
        
        Root<ANSWER> answer = cQ.from(fbAnalyis.getClassAnswer());
        Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
        Join<DATA,SURVEY> jSurvey = jData.join(JeeslSurveyData.Attributes.survey.toString());
        predicates.add(jSurvey.in(survey));
        
        Path<QUESTION> pQuestion = answer.get(JeeslSurveyAnswer.Attributes.question.toString());
        predicates.add(cB.equal(pQuestion,question));
        
        Expression<Long> eBoolean = cB.count(answer.<Long>get(JeeslSurveyAnswer.Attributes.valueBoolean.toString()));
      
        cQ.groupBy(pQuestion.get("id"),answer.get(JeeslSurveyAnswer.Attributes.valueBoolean.toString()));
        cQ.multiselect(pQuestion.get("id"),answer.get(JeeslSurveyAnswer.Attributes.valueBoolean.toString()),eBoolean);

        cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Tuple> tQ = em.createQuery(cQ);
        List<Tuple> tuples = tQ.getResultList();
        
        JsonFlatFigures result = JsonFlatFiguresFactory.build();
        for(Tuple t : tuples)
        {
	        	JsonFlatFigure f = JsonFlatFigureFactory.build();
	        	f.setL1((Long)t.get(0));
	        	f.setB1((Boolean)t.get(1));
	        	f.setL3((Long)t.get(2));
	        	result.getFigures().add(f);
        }
        
        return result;
	}
	
	@Override public JsonFlatFigures surveyCountOption(List<QUESTION> questions, SURVEY survey, List<CORRELATION> correlations)
	{
		if(questions==null || questions.isEmpty() || correlations==null || correlations.isEmpty()) {return JsonFlatFiguresFactory.build();} 
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
        Root<ANSWER> answer = cQ.from(fbAnalyis.getClassAnswer());
        
        Path<QUESTION> pQuestion = answer.get(JeeslSurveyAnswer.Attributes.question.toString());
        predicates.add(pQuestion.in(questions));
        
        Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
        Join<DATA,CORRELATION> jCorrelation = jData.join(JeeslSurveyData.Attributes.correlation.toString());
        predicates.add(jCorrelation.in(correlations));
        
        Path<OPTION> pOption = answer.get(JeeslSurveyAnswer.Attributes.option.toString());
        
        Expression<Long> eTa = cB.count(answer.<Long>get(JeeslSurveyAnswer.Attributes.option.toString()));
      
        cQ.groupBy(pQuestion.get("id"),pOption.get("id"));
        cQ.multiselect(pQuestion.get("id"),pOption.get("id"),eTa);
        
        cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Tuple> tQ = em.createQuery(cQ);
        List<Tuple> tuples = tQ.getResultList();
        
        JsonFlatFigures result = JsonFlatFiguresFactory.build();
        for(Tuple t : tuples)
        {
	        	JsonFlatFigure f = JsonFlatFigureFactory.build();
	        	f.setL1((Long)t.get(0));
	        	f.setL2((Long)t.get(1));
	        	f.setL3((Long)t.get(2));
	        	result.getFigures().add(f);
        }
        
        return result;
	}
}