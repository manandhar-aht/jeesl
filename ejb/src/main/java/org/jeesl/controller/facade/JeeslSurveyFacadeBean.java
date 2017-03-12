package org.jeesl.controller.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.interfaces.model.survey.JeeslSurvey;
import org.jeesl.interfaces.model.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSurveyFacadeBean <L extends UtilsLang,
									D extends UtilsDescription,
									SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									SS extends UtilsStatus<SS,L,D>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									TS extends UtilsStatus<TS,L,D>,
									TC extends UtilsStatus<TC,L,D>,
									SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
									CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
	extends UtilsFacadeBean implements JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslSurveyFacadeBean.class);
	
	private final Class<SURVEY> cSurvey;
	private final Class<TEMPLATE> cTemplate;
	private final Class<VERSION> cVersion;
	private final Class<TS> cTS;
	private final Class<SECTION> cSection;
	private final Class<ANSWER> cAnswer;
	private final Class<DATA> cData;
	private final Class<OPTION> cOption;
	private final Class<CORRELATION> cCorrelation;
	
	private SurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> ffSurvey;
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efAnswer;
	private EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> eTemplate;
	
	public JeeslSurveyFacadeBean(EntityManager em, Class<SURVEY> cSurvey, Class<TEMPLATE> cTemplate, Class<VERSION> cVersion, final Class<TS> cTS, Class<SECTION> cSection, Class<QUESTION> cQuestion, Class<ANSWER> cAnswer,  Class<DATA> cData, final Class<OPTION> cOption, final Class<CORRELATION> cCorrelation)
	{
		super(em);
		this.cSurvey=cSurvey;
		this.cTemplate=cTemplate;
		this.cVersion=cVersion;
		this.cTS=cTS;
		this.cSection=cSection;
		this.cAnswer=cAnswer;
		this.cData=cData;
		this.cOption=cOption;
		this.cCorrelation=cCorrelation;
		
		ffSurvey = SurveyFactoryFactory.factory(cSurvey,cTemplate,cVersion,cSection,cQuestion,cAnswer,cData,cOption);
		eTemplate = ffSurvey.template();
		efAnswer = ffSurvey.answer();
	}

	@Override public TEMPLATE load(TEMPLATE template)
	{
		template = em.find(cTemplate,template.getId());
		template.getSections().size();
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
	
	@Override public TEMPLATE fcSurveyTemplate(TC category, TS status){return fcSurveyTemplate(category,null,status);}
	@Override public TEMPLATE fcSurveyTemplate(TC category, VERSION version, TS status)
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
			em.persist(t);
			return t;
		}
		else{return list.get(0);}
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
//		logger.info("Now Sabing ...");
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