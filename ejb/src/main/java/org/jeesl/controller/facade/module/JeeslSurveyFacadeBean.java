package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Tuple;
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
import org.jeesl.factory.factory.survey.SurveyCoreFactoryFactory;
import org.jeesl.factory.factory.survey.SurveyTemplateFactoryFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFigureFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFiguresFactory;
import org.jeesl.factory.txt.system.status.TxtStatusFactory;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurveyType;
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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;
import org.jeesl.model.json.JsonFlatFigure;
import org.jeesl.model.json.JsonFlatFigures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslSurveyFacadeBean <L extends UtilsLang, D extends UtilsDescription,
				SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS>,
				VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS>,
				TS extends UtilsStatus<TS,L,D>,
				TC extends UtilsStatus<TC,L,D>,
				SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
				DOMAIN extends JeeslSurveyDomain<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				PATH extends JeeslSurveyDomainPath<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
	extends UtilsFacadeBean implements JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
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
	private final Class<MATRIX> cMatrix;
	private final Class<DATA> cData;
	private final Class<OPTION> cOption;
	private final Class<CORRELATION> cCorrelation;
	private final Class<AQ> cAq;
	
	private final SurveyTemplateFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT> ffTemplate;
	private final SurveyCoreFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffSurvey;
	
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efAnswer;
	private EjbSurveyTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT> eTemplate;
	
	public JeeslSurveyFacadeBean(EntityManager em, Class<L> cL, Class<D> cD, Class<SURVEY> cSurvey, final Class<SS> cSs, Class<SCHEME> cScheme, Class<TEMPLATE> cTemplate, Class<VERSION> cVersion, final Class<TS> cTS, Class<SECTION> cSection, Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<UNIT> cUnit, final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption, final Class<CORRELATION> cCorrelation, final Class<AQ> cAq, final Class<ATT> cAtt)
	{
		super(em);
		this.cSurvey=cSurvey;
		this.cTemplate=cTemplate;
		this.cVersion=cVersion;
		this.cTS=cTS;
		this.cSection=cSection;
		this.cQuestion=cQuestion;
		this.cAnswer=cAnswer;
		this.cMatrix=cMatrix;
		this.cData=cData;
		this.cOption=cOption;
		this.cCorrelation=cCorrelation;
		this.cAq=cAq;
		
		ffTemplate = SurveyTemplateFactoryFactory.factory(cL,cD,cSurvey,cSs,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption);
		ffSurvey = SurveyCoreFactoryFactory.factory(cL,cD,cSurvey,cSs,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption);
		
		eTemplate = ffTemplate.template();
		efAnswer = ffSurvey.answer();
	}

	@Override public TEMPLATE load(TEMPLATE template,boolean withQuestions, boolean withOptions)
	{
		template = em.find(cTemplate,template.getId());
		
		template.getSchemes().size();
		template.getOptionSets().size();
		if(withQuestions)
		{
			for(SECTION section : template.getSections())
			{
				if(withOptions)
				{
					for(QUESTION question : section.getQuestions())
					{
						question.getOptions().size();
					}
				}
				else
				{
					section.getQuestions().size();
				}
			}
		}
		else
		{
			template.getSections().size();
		}
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
	
	@Override public OPTIONS load(OPTIONS optionSet)
	{
		optionSet = em.find(ffSurvey.getOptionSetClass(),optionSet.getId());
		optionSet.getOptions().size();
		return optionSet;
	}
	
	@Override public SURVEY fSurvey(CORRELATION correlation) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SURVEY> cQ = cB.createQuery(cSurvey);
		Root<DATA> data = cQ.from(cData);
		
		Path<SURVEY> pathSurvey = data.get(JeeslSurveyData.Attributes.survey.toString());
		Join<DATA,CORRELATION> jCorrelation = data.join(JeeslSurveyData.Attributes.correlation.toString());
		
		cQ.where(cB.equal(jCorrelation,correlation));
		cQ.select(pathSurvey);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException e){e.printStackTrace(); throw new UtilsNotFoundException("No survey found for this correlation");}
		catch (NonUniqueResultException e){e.printStackTrace(); throw new UtilsNotFoundException("Multiple surveys found for this correlation");}
	}
	
	@Override public void deleteSurvey(SURVEY survey) throws UtilsConstraintViolationException, UtilsLockingException
	{
//		survey = em.find(cSurvey, survey.getId());
		this.rmProtected(survey);
	}
	
	@Override public List<SURVEY> fSurveysForCategories(List<TC> categories)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<SURVEY>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SURVEY> cQ = cB.createQuery(cSurvey);
		Root<SURVEY> survey = cQ.from(cSurvey);
		
		Join<SURVEY,TEMPLATE> jTemplate = survey.join(JeeslSurvey.Attributes.template.toString());
		Path<TC> pCategory = jTemplate.get(JeeslSurveyTemplate.Attributes.category.toString());
		
		predicates.add(pCategory.in(categories));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(survey);

		return em.createQuery(cQ).getResultList();
	}
	@Override public List<SURVEY> fSurveys(TC category, SS status, Date date)
	{
		List<TC> categories = new ArrayList<TC>();categories.add(category);
		return this.fSurveys(categories, status, date);
	}
	@Override public List<SURVEY> fSurveys(List<TC> categories, SS status, Date date)
	{
		if(categories==null || categories.isEmpty()) {return new ArrayList<SURVEY>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SURVEY> cQ = cB.createQuery(cSurvey);
		Root<SURVEY> survey = cQ.from(cSurvey);
		
		Join<SURVEY,TEMPLATE> jTemplate = survey.join(JeeslSurvey.Attributes.template.toString());
		Path<TC> pCategory = jTemplate.get(JeeslSurveyTemplate.Attributes.category.toString());
		Path<SS> pStatus = survey.get(JeeslSurvey.Attributes.status.toString());
		
		predicates.add(pCategory.in(categories));
		predicates.add(cB.equal(pStatus, status));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(survey);

		return em.createQuery(cQ).getResultList();
	}
	
	@Override public void rmVersion(VERSION version) throws UtilsConstraintViolationException
	{
		version = em.find(cVersion, version.getId());
		this.rmProtected(version);
	}
	
	@Override public OPTION saveOption(QUESTION question, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException
	{
		question = em.find(cQuestion, question.getId());
		option = this.saveProtected(option);
		if(!question.getOptions().contains(option))
		{
			question.getOptions().add(option);
			this.save(question);
		}
		return option;
	}
	@Override public OPTION saveOption(OPTIONS set, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException
	{
		set = em.find(ffSurvey.getOptionSetClass(), set.getId());
		option = this.saveProtected(option);
		if(!set.getOptions().contains(option))
		{
			set.getOptions().add(option);
			this.save(set);
		}
		return option;
	}
	
	@Override public void rmOption(QUESTION question, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException
	{
		question = em.find(cQuestion, question.getId());
		option = em.find(cOption, option.getId());
		if(question.getOptions().contains(option))
		{
			question.getOptions().remove(option);
			this.save(question);
		}
		this.rmProtected(option);
	}
	@Override public void rmOption(OPTIONS set, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException
	{
		set = em.find(ffSurvey.getOptionSetClass(), set.getId());
		option = em.find(cOption, option.getId());
		if(set.getOptions().contains(option))
		{
			set.getOptions().remove(option);
			this.save(set);
		}
		this.rmProtected(option);
	}
	
	@Override public <TYPE extends UtilsStatus<TYPE,L,D>, WT extends JeeslWithType<L,D,TYPE>, W extends JeeslWithSurveyType<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT,WT,TYPE>>
		List<W> fWithSurveys(Class<W> c, List<SS> status, TYPE type, Date date)
	{
		logger.info("Looking für wSurvey for type="+type.getCode()+" and status="+TxtStatusFactory.toCodes(status)+" and c="+c.getSimpleName());
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<W> cQ = cB.createQuery(c);
		Root<W> with = cQ.from(c);
		
		Join<W,SURVEY> jSurvey = with.join(JeeslWithSurvey.Attributes.survey.toString());
		Join<SURVEY,SS> jStatus = jSurvey.join(JeeslSurvey.Attributes.status.toString());
		Path<TYPE> pType = with.get(JeeslWithType.attributeType);
		
		predicates.add(jStatus.in(status));
		predicates.add(cB.equal(pType,type));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(with);
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public <W extends JeeslWithSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>>
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
	
	@Override public <W extends JeeslWithSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>>
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
			if(version!=null) {logger.info("\tVersion: "+version.toString()+" (unsaved:"+EjbIdFactory.isUnSaved(version)+")");}
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
		if(sections!=null && sections.isEmpty()){return new ArrayList<ANSWER>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ANSWER> cQ = cB.createQuery(cAnswer);
		Root<ANSWER> answer = cQ.from(cAnswer);
		Expression<Long> eAnswerId = answer.get(EjbWithId.attribute);
		
		Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
		predicates.add(cB.equal(jData,data));
		
		Join<ANSWER,QUESTION> jQuestion = answer.join(JeeslSurveyAnswer.Attributes.question.toString());
		if(visible!=null){predicates.add(cB.equal(jQuestion.<Boolean>get(JeeslSurveyQuestion.Attributes.visible.toString()),visible));}
		Expression<Integer> eQuestionPosition = jQuestion.get(JeeslSurveyQuestion.Attributes.position.toString());
		
		Join<QUESTION,SECTION> jSection = jQuestion.join(JeeslSurveyQuestion.Attributes.section.toString());
		if(visible!=null){predicates.add(cB.equal(jSection.<Boolean>get(JeeslSurveySection.Attributes.visible.toString()),visible));}
		Expression<Integer> eSectionPosition = jSection.get(JeeslSurveySection.Attributes.position.toString());
		if(sections!=null){predicates.add(jSection.in(sections));}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(answer);
		cQ.orderBy(cB.asc(eSectionPosition),cB.asc(eQuestionPosition),cB.asc(eAnswerId));
		
		TypedQuery<ANSWER> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public List<ANSWER> fAnswers(List<DATA> datas)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ANSWER> cQ = cB.createQuery(cAnswer);
		Root<ANSWER> answer = cQ.from(cAnswer);
		Expression<Long> eAnswerId = answer.get(EjbWithId.attribute);
		
		Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
		predicates.add(jData.in(datas));
			
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(answer);
//		cQ.orderBy(cB.asc(eSectionPosition),cB.asc(eQuestionPosition),cB.asc(eAnswerId));
		
		TypedQuery<ANSWER> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public List<ANSWER> fAnswers(SURVEY survey, QUESTION question)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ANSWER> cQ = cB.createQuery(cAnswer);
		Root<ANSWER> answer = cQ.from(cAnswer);
		
		Join<ANSWER,DATA> jData = answer.join(JeeslSurveyAnswer.Attributes.data.toString());
		Join<DATA,SURVEY> jSurvey = jData.join(JeeslSurveyData.Attributes.survey.toString());
		predicates.add(cB.equal(jSurvey,survey));
		
		Join<ANSWER,QUESTION> jQuestion = answer.join(JeeslSurveyAnswer.Attributes.question.toString());
		predicates.add(cB.equal(jQuestion,question));
	
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(answer);
		
		TypedQuery<ANSWER> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public List<MATRIX> fCells(List<ANSWER> answers) 
	{
		if(answers!=null && answers.isEmpty()){return new ArrayList<MATRIX>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<MATRIX> cQ = cB.createQuery(cMatrix);
		Root<MATRIX> matrix = cQ.from(cMatrix);
		
		Join<MATRIX,ANSWER> jAnswer = matrix.join(JeeslSurveyMatrix.Attributes.answer.toString());
		predicates.add(jAnswer.in(answers));

		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(matrix);
		
		TypedQuery<MATRIX> tQ = em.createQuery(cQ);
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

	@Override public DATA fData(CORRELATION correlation) throws UtilsNotFoundException
	{
		return this.oneForParent(cData, "correlation", correlation);
	}
	
	@Override public List<DATA> fDatas(List<CORRELATION> correlations)
	{
		if(correlations!=null && correlations.isEmpty()){return new ArrayList<DATA>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(cData);
		Root<DATA> data = cQ.from(cData);
		
		Join<DATA,CORRELATION> jCorrelation = data.join(JeeslSurveyData.Attributes.correlation.toString());
		predicates.add(jCorrelation.in(correlations));

		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		
		TypedQuery<DATA> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public ANSWER saveAnswer(ANSWER answer) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(answer.getOption()!=null){answer.setOption(this.find(cOption,answer.getOption()));}
		return this.saveProtected(answer);
	}

	
	@Override public void rmAnswer(ANSWER answer) throws UtilsConstraintViolationException
	{
		answer = em.find(cAnswer, answer.getId());
		answer.getData().getAnswers().remove(answer);
		this.rmProtected(answer);
	}
	
	@Override public JsonFlatFigures surveyCountAnswer(List<QUESTION> questions, SURVEY survey, List<CORRELATION> correlations)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
        
        Root<ANSWER> answer = cQ.from(cAnswer);
        
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
        
        Root<ANSWER> answer = cQ.from(cAnswer);
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
        
        Root<ANSWER> answer = cQ.from(cAnswer);
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
        Root<ANSWER> answer = cQ.from(cAnswer);
        
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