package org.jeesl.controller.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSystemJobFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>
									>
					extends UtilsFacadeBean
					implements JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>
{	
	@SuppressWarnings("unused")
	private final Class<TEMPLATE> cTemplate;
	@SuppressWarnings("unused")
	private final Class<JOB> cJob;
	
	public JeeslSystemJobFacadeBean(EntityManager em,final Class<TEMPLATE> cTemplate,final Class<JOB> cJob)
	{
		super(em);
		this.cTemplate=cTemplate;
		this.cJob=cJob;
	}
	
	@Override public List<JOB> fJobs(List<CATEGORY> categories, List<STATUS> status)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<JOB>();}
		if(status==null || status.isEmpty()){return new ArrayList<JOB>();}
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<JOB> cQ = cB.createQuery(cJob);
		Root<JOB> mail = cQ.from(cJob);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<Date> pRecordCreation = mail.get(JeeslJob.Attributes.recordCreation.toString());
		Path<CATEGORY> pCategory = mail.get(JeeslJob.Attributes.category.toString());
		Path<STATUS> pStatus = mail.get(JeeslJob.Attributes.status.toString());
		
		predicates.add(pCategory.in(categories));
		predicates.add(pStatus.in(status));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.desc(pRecordCreation));
		cQ.select(mail);

		TypedQuery<JOB> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
}