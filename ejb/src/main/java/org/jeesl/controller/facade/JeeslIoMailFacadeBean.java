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

import org.jeesl.factory.ejb.system.io.mail.EjbIoMailFactory;
import org.jeesl.factory.factory.MailFactoryFactory;
import org.jeesl.interfaces.facade.JeeslIoMailFacade;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoMailFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoMailFacadeBean.class);
		
	@SuppressWarnings("unused")
	private final Class<CATEGORY> cCategory;
	
	private final Class<MAIL> cMail;
	private final Class<STATUS> cStatus;
	
	private EjbIoMailFactory<L,D,CATEGORY,MAIL,STATUS> efMail;
	
	public JeeslIoMailFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus)
	{
		super(em);
		this.cCategory=cCategory;
		this.cMail=cMail;
		this.cStatus=cStatus;
		MailFactoryFactory<L,D,CATEGORY,MAIL,STATUS> ff = MailFactoryFactory.factory(cMail);
		efMail = ff.mail();
	}
	
	@Override public List<MAIL> fMails(List<CATEGORY> categories, List<STATUS> status)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<MAIL>();}
		if(status==null || status.isEmpty()){return new ArrayList<MAIL>();}
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<MAIL> cQ = cB.createQuery(cMail);
		Root<MAIL> mail = cQ.from(cMail);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<Date> pRecordCreation = mail.get(JeeslIoMail.Attributes.recordCreation.toString());
		Path<CATEGORY> pCategory = mail.get(JeeslIoMail.Attributes.category.toString());
		Path<STATUS> pStatus = mail.get(JeeslIoMail.Attributes.status.toString());
		
		predicates.add(pCategory.in(categories));
		predicates.add(pStatus.in(status));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.desc(pRecordCreation));
		cQ.select(mail);

		TypedQuery<MAIL> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public void queueMail(CATEGORY category, org.jeesl.model.xml.system.io.mail.Mail mail) throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		STATUS status = this.fByCode(cStatus, JeeslIoMail.Status.queue);
		MAIL ejb = efMail.build(category,status,mail);
		ejb = this.persist(ejb);
		logger.info(cMail.getSimpleName()+" spooled with id="+ejb.getId());
	}

	@Override public List<MAIL> fSpoolMails(int maxResult)
	{
		List<MAIL> mails = new ArrayList<MAIL>();
		try
		{
			STATUS statusSpooling = fByCode(cStatus, JeeslIoMail.Status.spooling);
			STATUS statusQueue = fByCode(cStatus, JeeslIoMail.Status.queue);
			
			mails.addAll(fMails(statusSpooling,maxResult));
			
			if(mails.size()<maxResult)
			{
				mails.addAll(fMails(statusQueue,maxResult-mails.size()));
			}
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		
		return mails;
	}
	
	public List<MAIL> fMails(STATUS status, int maxResult)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<MAIL> cQ = cB.createQuery(cMail);
		Root<MAIL> mail = cQ.from(cMail);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<STATUS> pStatus = mail.get(JeeslIoMail.Attributes.status.toString());
		Path<Date> pRecordCreation = mail.get(JeeslIoMail.Attributes.recordCreation.toString());
		Path<Date> pRecordSpool = mail.get(JeeslIoMail.Attributes.recordSpool.toString());
		
		DateTime dt = new DateTime();
		predicates.add(cB.equal(pStatus,status));
		predicates.add(cB.or(cB.isNull(pRecordSpool),cB.lessThanOrEqualTo(pRecordSpool, dt.minusMinutes(5).toDate())));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.desc(pRecordCreation));
		cQ.select(mail);

		TypedQuery<MAIL> tQ = em.createQuery(cQ);
		tQ.setMaxResults(maxResult);
		
		return tQ.getResultList();
	}
}