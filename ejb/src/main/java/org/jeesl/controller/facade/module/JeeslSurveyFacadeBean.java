package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSurveyFacadeBean <L extends UtilsLang, D extends UtilsDescription,
									SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									SS extends UtilsStatus<SS,L,D>,
									SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									TS extends UtilsStatus<TS,L,D>,
									TC extends UtilsStatus<TC,L,D>,
									SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
									CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
	extends UtilsFacadeBean implements JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslSurveyFacadeBean.class);
	
	private final Class<SURVEY> cSurvey;
	private final Class<TEMPLATE> cTemplate;
	private final Class<VERSION> cVersion;
	@SuppressWarnings("unused")
	private final Class<TS> cTS;
	private final Class<SECTION> cSection;
	private final Class<QUESTION> cQuestion;
	private final Class<ANSWER> cAnswer;
	private final Class<DATA> cData;
	private final Class<OPTION> cOption;
	private final Class<CORRELATION> cCorrelation;
	
	private SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> ffSurvey;
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efAnswer;
	private EjbSurveyTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> eTemplate;
	
	public JeeslSurveyFacadeBean(EntityManager em, Class<SURVEY> cSurvey, Class<SCHEME> cScheme, Class<TEMPLATE> cTemplate, Class<VERSION> cVersion, final Class<TS> cTS, Class<SECTION> cSection, Class<QUESTION> cQuestion, final Class<SCORE> cScore, Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, Class<DATA> cData, final Class<OPTION> cOption, final Class<CORRELATION> cCorrelation)
	{
		super(em);
		this.cSurvey=cSurvey;
		this.cTemplate=cTemplate;
		this.cVersion=cVersion;
		this.cTS=cTS;
		this.cSection=cSection;
		this.cQuestion=cQuestion;
		this.cAnswer=cAnswer;
		this.cData=cData;
		this.cOption=cOption;
		this.cCorrelation=cCorrelation;
		
		ffSurvey = SurveyFactoryFactory.factory(cSurvey,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cAnswer,cMatrix,cData,cOption);
		eTemplate = ffSurvey.template();
		efAnswer = ffSurvey.answer();
	}

	@Override public TEMPLATE load(TEMPLATE template)
	{
		template = em.find(cTemplate,template.getId());
		template.getSections().size();
		template.getSchemes().size();
		return template;
	}
	
	@Override public SECTION load(SECTION section)
	{
		section = em.find(cSection,section.getId());
		for(SECTION sub : section.getSections())
		{
			sub.getQuestions().size();
		}
		section.getQuestions().size();
		return section;
	}
	
	@Override public SURVEY load(SURVEY survey)
	{
		survey = em.find(cSurvey,survey.getId());
		survey.getSurveyData().size();
		return survey;
	}
	
	@Override public QUESTION load(QUESTION question)
	{
		question = em.find(cQuestion,question.getId());
		question.getScores().size();
		question.getOptions().size();
		return question;
	}
	
	@Override public ANSWER load(ANSWER answer)
	{
		answer = em.find(cAnswer,answer.getId());
		answer.getMatrix().size();
		answer.getOptions().size();
		return answer;
	}

	@Override public DATA load(DATA data)
	{
		data = em.find(cData,data.getId());
		data.getAnswers().size();
		return data;
	}
	
	@Override public void rmVersion(VERSION version) throws UtilsConstraintViolationException
	{
		version = em.find(cVersion, version.getId());
		this.rmProtected(version);
	}
	
	@Override public void rmOption(OPTION option) throws UtilsConstraintViolationException
	{
		option = em.find(cOption, option.getId());
		option.getQuestion().getOptions().remove(option);
		this.rmProtected(option);
	}
	
	@Override public <W extends JeeslWithSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
		List<W> fSurveys(Class<W> c, List<SS> status, Date date)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<W> cQ = cB.createQuery(c);
		Root<W> root = cQ.from(c);
		
		Join<W,SURVEY> jSurvey = root.join(JeeslWithSurvey.Attributes.survey.toString());
		Path<SS> pStatus = jSurvey.get(JeeslSurvey.Attributes.status.toString());
		
		predicates.add(pStatus.in(status));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);

		return em.createQuery(cQ).getResultList();
	}
	
	@Override public <W extends JeeslWithSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
		W fWithSurvey(Class<W> c, long surveyId) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<W> cQ = cB.createQuery(c);
		Root<W> root = cQ.from(c);
		
		Join<W,SURVEY> jSurvey = root.join(JeeslWithSurvey.Attributes.survey.toString());
		Expression<Long> eId = jSurvey.get(JeeslSurvey.Attributes.id.toString());
		
		predicates.add(cB.equal(eId,surveyId));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);
	
		TypedQuery<W> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException(c.getSimpleName()+" not found for "+JeeslSurvey.class.getSimpleName()+"."+JeeslSurvey.Attributes.id+"="+surveyId);}
	}
	
	@Override public List<VERSION> fVersions(TC category)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<VERSION> cQ = cB.createQuery(cVersion);
		Root<VERSION> root = cQ.from(cVersion);
		
		Join<VERSION,TEMPLATE> jTemplate = root.join(JeeslSurveyTemplateVersion.Attributes.template.toString());
		Path<TC> pCategory = jTemplate.get(JeeslSurveyTemplate.Attributes.category.toString());
		Path<Date> pRecord = root.get(JeeslSurveyTemplateVersion.Attributes.record.toString());
		
		predicates.add(cB.equal(pCategory,category));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.desc(pRecord));
		cQ.select(root);

		return em.createQuery(cQ).getResultList();
	}
	
	@Override public TEMPLATE fcSurveyTemplate(TC category, TS status){return fcSurveyTemplate(category,null,status,null);}
	@Override public TEMPLATE fcSurveyTemplate(TC category, VERSION version, TS status, VERSION nestedVersion)
	{
		if(logger.isInfoEnabled())
		{
			logger.info("Query:");
			logger.info("\tCategory: "+category.getCode());
			if(version!=null)
			{
				logger.info("\tVersion: "+version.toString()+" (unsaved:"+EjbIdFactory.isUnSaved(version)+")");
			}
			logger.info("\tStatus: "+status.getCode());
		}
		
		if(version!=null && EjbIdFactory.isUnSaved(version))
		{
			TEMPLATE template = eTemplate.build(category,status,"");
			template.setVersion(version);
			template.getVersion().setTemplate(template);
			if(nestedVersion!=null){template.setNested(nestedVersion.getTemplate());}
			em.persist(template);
			return template;
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TEMPLATE> cQ = cB.createQuery(cTemplate);
		Root<TEMPLATE> template = cQ.from(cTemplate);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<TC> pCategory = template.get(JeeslSurveyTemplate.Attributes.category.toString());
		predicates.add(cB.equal(pCategory,category));
		
		if(version!=null)
		{
			logger.info("Using Version: "+version.toString());
			Join<TEMPLATE,VERSION> jVersion = template.join(JeeslSurveyTemplate.Attributes.version.toString());
//			predicates.add(cB.equal(jVersion,version));
			predicates.add(cB.isTrue(jVersion.in(version.getId())));
		}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(template);

		List<TEMPLATE> list = em.createQuery(cQ).getResultList();
		if(logger.isInfoEnabled())
		{
			logger.info("Results: "+list.size());
			for(TEMPLATE t : list)
			{
				logger.info("\t"+t.toString());
			}
		}
		
		if(list.isEmpty())
		{
			TEMPLATE t = eTemplate.build(category,status,"");
			if(nestedVersion!=null){t.setNested(nestedVersion.getTemplate());}
			em.persist(t);
			return t;
		}
		else{return list.get(0);}
	}
	
	@Override public List<ANSWER> fAnswers(DATA data, Boolean visible, List<SECTION> sections)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ANSWER> cQ = cB.createQuery(cAnswer);
		Root<ANSWER> answer = cQ.from(cAnswer);
		
		Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
		predicates.add(cB.equal(jData,data));
		
		Join<ANSWER,QUESTION> jQuestion = answer.join(JeeslSurveyAnswer.Attributes.question.toString());
		predicates.add(cB.equal(jQuestion.<Boolean>get(JeeslSurveyQuestion.Attributes.visible.toString()),true));
		Expression<Integer> eQuestionPosition = jQuestion.get(JeeslSurveyQuestion.Attributes.position.toString());
		
		Join<QUESTION,SECTION> jSection = answer.join(JeeslSurveyQuestion.Attributes.section.toString());
		predicates.add(cB.equal(jSection.<Boolean>get(JeeslSurveySection.Attributes.visible.toString()),true));
		Expression<Integer> eSectionPosition = jSection.get(JeeslSurveySection.Attributes.position.toString());
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(answer);
		cQ.orderBy(cB.asc(eSectionPosition),cB.asc(eQuestionPosition));
		
		TypedQuery<ANSWER> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public List<ANSWER> fcAnswers(DATA data)
	{
		data = em.find(cData,data.getId());
		TEMPLATE template = em.find(cTemplate,data.getSurvey().getTemplate().getId());
		List<ANSWER> result = new ArrayList<ANSWER>();

		Set<Long> existing = new HashSet<Long>();
		for(ANSWER a : data.getAnswers()){existing.add(a.getQuestion().getId());result.add(a);}
		createAnswers(existing,template.getSections(),data,result);
		
		return result;
	}
	
	private void createAnswers(Set<Long> existing, List<SECTION> sections, DATA data, List<ANSWER> result)
	{
		for(SECTION s : sections)
		{
			for(QUESTION q : s.getQuestions())
			{
				if(!existing.contains(q.getId()))
				{
					try
					{
						ANSWER answer = this.persist(efAnswer.build(q,data));
						result.add(answer);
					}
					catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				}
			}
			createAnswers(existing,s.getSections(),data,result);
		}
	}

	@Override public DATA saveData(DATA data) throws UtilsConstraintViolationException, UtilsLockingException
	{
//		logger.info("Testing Correlation: null?"+(data.getCorrelation()==null));
//		logger.info("Testing Correlation: Saved?"+EjbIdFactory.isSaved(data.getCorrelation()));
		
		if(EjbIdFactory.isSaved(data.getCorrelation()))
		{
			data.setCorrelation(em.find(cCorrelation,data.getCorrelation().getId()));
		}
//		logger.info("Now Saving ...");
		return this.saveProtected(data);
	}

	@Override public List<ANSWER> fAnswers(SURVEY survey)
	{
		return this.allForGrandParent(cAnswer, cData, "data", survey, "survey");
	}

	@Override
	public DATA fData(CORRELATION correlation) throws UtilsNotFoundException
	{
		return this.oneForParent(cData, "correlation", correlation);
	}
	
	@Override public ANSWER saveAnswer(ANSWER answer) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(answer.getOption()!=null){answer.setOption(this.find(cOption,answer.getOption()));}
		return this.saveProtected(answer);
	}
}