package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslBbFacade;
import org.jeesl.factory.builder.module.BbFactoryBuilder;
import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class JeeslBulletinBoardFacadeBean<L extends UtilsLang,D extends UtilsDescription,
										SCOPE extends UtilsStatus<SCOPE,L,D>,
										BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,POST,USER>,
										PUB extends UtilsStatus<PUB,L,D>,
										POST extends JeeslBbPost<BB,USER>,
										USER extends EjbWithEmail>
					extends UtilsFacadeBean
					implements JeeslBbFacade<L,D,SCOPE,BB,PUB,POST,USER>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslBulletinBoardFacadeBean.class);
	
	private final BbFactoryBuilder<L,D,SCOPE,BB,PUB,POST,USER> fbBb;
	
	public JeeslBulletinBoardFacadeBean(EntityManager em, final BbFactoryBuilder<L,D,SCOPE,BB,PUB,POST,USER> fbBb)
	{
		super(em);
		this.fbBb=fbBb;
	}
	
	@Override
	public List<BB> fBulletinBoards(SCOPE scope, long refId)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<BB> cQ = cB.createQuery(fbBb.getClassBoard());
		Root<BB> bb = cQ.from(fbBb.getClassBoard());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Expression<Long> eRefId = bb.get(JeeslBbBoard.Attributes.refId.toString());
		Path<SCOPE> pScope = bb.get(JeeslBbBoard.Attributes.scope.toString());
		
		predicates.add(cB.equal(eRefId,refId));
		predicates.add(cB.equal(pScope,scope));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(bb);

		TypedQuery<BB> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
}