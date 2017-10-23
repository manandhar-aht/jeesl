package org.jeesl.controller.facade.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.factory.builder.JobFactoryBuilder;
import org.jeesl.factory.ejb.system.job.EjbJobCacheFactory;
import org.jeesl.factory.ejb.system.job.EjbJobFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.joda.time.DateTime;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class JeeslSystemJobFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									FEEDBACK extends JeeslJobFeedback<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									FT extends UtilsStatus<FT,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									USER extends EjbWithEmail
									>
					extends UtilsFacadeBean
					implements JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>
{	
	private final Class<TEMPLATE> cTemplate;
	private final Class<JOB> cJob;
	private final Class<STATUS> cStatus;
	private final Class<CACHE> cCache;
	
	private EjbJobCacheFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> efCache;
	private EjbJobFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> efJob;
	
	public JeeslSystemJobFacadeBean(EntityManager em,JobFactoryBuilder<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fbJob, final Class<TEMPLATE> cTemplate, final Class<JOB> cJob, final Class<STATUS> cStatus, final Class<ROBOT> cRobot, final Class<CACHE> cCache)
	{
		super(em);
		this.cTemplate=cTemplate;
		this.cJob=cJob;
		this.cStatus=cStatus;
		this.cCache=cCache;
		
		efCache = fbJob.cache();
		efJob = fbJob.job();
	}
	
	@Override public <E extends Enum<E>> TEMPLATE fJobTemplate(E type, String code) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TEMPLATE> cQ = cB.createQuery(cTemplate);
		Root<TEMPLATE> template = cQ.from(cTemplate);
		
		Join<TEMPLATE,TYPE> jType = template.join(JeeslJobTemplate.Attributes.type.toString());
		Expression<String> eType = jType.get(UtilsStatus.EjbAttributes.code.toString());
		Expression<String> eCode = template.get(JeeslJobTemplate.Attributes.code.toString());
		
		predicates.add(cB.equal(eType,type.toString()));
		predicates.add(cB.equal(eCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(template);

		TypedQuery<TEMPLATE> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+cTemplate.getSimpleName()+" found for type="+type.toString()+" and code="+code);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("No unique results in "+cTemplate.getSimpleName()+" for type="+type.toString()+" and code="+code);}
	}
	
	@Override public List<JOB> fJobs(List<CATEGORY> categories, List<TYPE> types, List<STATUS> status, Date from, Date to)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<JOB>();}
		if(status==null || status.isEmpty()){return new ArrayList<JOB>();}
		if(types==null || types.isEmpty()){return new ArrayList<JOB>();}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<JOB> cQ = cB.createQuery(cJob);
		Root<JOB> job = cQ.from(cJob);
		
		Join<JOB,TEMPLATE> jTemplate = job.join(JeeslJob.Attributes.template.toString());
		Path<CATEGORY> pCategory = jTemplate.get(JeeslJobTemplate.Attributes.category.toString());
		Path<TYPE> pType = jTemplate.get(JeeslJobTemplate.Attributes.type.toString());
		
		Path<Date> pRecordCreation = job.get(JeeslJob.Attributes.recordCreation.toString());
		Path<STATUS> pStatus = job.get(JeeslJob.Attributes.status.toString());
		
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(pRecordCreation, (new DateTime(from)).withTimeAtStartOfDay().toDate()));}
		if(to!=null){predicates.add(cB.lessThan(pRecordCreation, (new DateTime(to)).withTimeAtStartOfDay().plusDays(1).toDate()));}
		
		predicates.add(pCategory.in(categories));
		predicates.add(pType.in(types));
		predicates.add(pStatus.in(status));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.desc(pRecordCreation));
		cQ.select(job);

		TypedQuery<JOB> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public JOB fActiveJob(TEMPLATE template, String code) throws UtilsNotFoundException
	{
		List<STATUS> statuses = new ArrayList<STATUS>();
		try
		{
			statuses.add(this.fByCode(cStatus,JeeslJob.Status.queue));
			statuses.add(this.fByCode(cStatus,JeeslJob.Status.working));
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<JOB> cQ = cB.createQuery(cJob);
		Root<JOB> job = cQ.from(cJob);
		
		Join<CACHE,TEMPLATE> jTemplate = job.join(JeeslJob.Attributes.template.toString());
		Path<STATUS> pStatus = job.get(JeeslJob.Attributes.status.toString());
		Expression<String> pCode = job.get(JeeslJob.Attributes.code.toString());
		
		predicates.add(cB.equal(jTemplate,template));
		predicates.add(pStatus.in(statuses));
		predicates.add(cB.equal(pCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(job);

		TypedQuery<JOB> tQ = em.createQuery(cQ);
		List<JOB> results = tQ.getResultList();
		if(results.isEmpty()){throw new UtilsNotFoundException("Nothing found for template:"+template.getCode()+" and job.code="+code);}
		else{return results.get(0);}
	}

	@Override public CACHE fJobCache(TEMPLATE template, String code) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<CACHE> cQ = cB.createQuery(cCache);
		Root<CACHE> cache = cQ.from(cCache);
		
		Join<CACHE,TEMPLATE> jTemplate = cache.join(JeeslJobCache.Attributes.template.toString());
		Expression<String> pCode = cache.get(JeeslJobCache.Attributes.code.toString());
		
		predicates.add(cB.equal(jTemplate,template));
		predicates.add(cB.equal(pCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(cache);

		TypedQuery<CACHE> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found for template:"+template.getCode()+" and cache.code="+code);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Results not unique for template:"+template.getCode()+" and cache.code="+code);}
	}

	@Override
	public CACHE uJobCache(TEMPLATE template, String code, byte[] data) throws UtilsConstraintViolationException, UtilsLockingException
	{
		CACHE cache = null;
		
		try {cache = fJobCache(template,code);}
		catch (UtilsNotFoundException e){cache = efCache.build(template, code, data);}
		
		cache.setRecord(new Date());
		cache.setData(data);
		cache = this.save(cache);
		
		return cache;
	}

	@Override
	public JOB cJob(USER user, List<FEEDBACK> feedbacks, TEMPLATE template, String code, String name) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		STATUS status = this.fByCode(cStatus,JeeslJob.Status.queue);
		JOB job = efJob.build(user,template,status,code,name);
		return this.save(job);
	}
}