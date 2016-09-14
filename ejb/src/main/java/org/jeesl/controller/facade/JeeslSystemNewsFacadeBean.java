package org.jeesl.controller.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.interfaces.facade.JeeslSystemNewsFacade;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidFrom;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidUntil;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslSystemNewsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
										USER extends EjbWithId>
					extends UtilsFacadeBean
					implements JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslSystemNewsFacadeBean.class);
	
	private final Class<NEWS> cNews;
	
	public JeeslSystemNewsFacadeBean(EntityManager em, final Class<NEWS> cNews)
	{
		super(em);
		this.cNews=cNews;
	}

	@Override
	public List<NEWS> fActiveNews()
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<NEWS> cQ = cB.createQuery(cNews);
		Root<NEWS> news = cQ.from(cNews);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<Boolean> pathVisible = news.get(JeeslSystemNews.Attributes.visible.toString());
		Expression<Date> dStart = news.get(EjbWithValidFrom.Attributes.validFrom.toString());
		Expression<Date> dEnd   = news.get(EjbWithValidUntil.Attributes.validUntil.toString());
		
		LocalDate date = new LocalDateTime().toLocalDate();
		predicates.add(cB.isTrue(pathVisible));
		predicates.add(cB.lessThanOrEqualTo(dStart,date.toDate()));
		predicates.add(cB.greaterThanOrEqualTo(dEnd,date.toDate()));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(news);
		cQ.orderBy(cB.desc(dStart));

		TypedQuery<NEWS> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
}