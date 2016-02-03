package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.interfaces.facade.UtilsUserFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class UtilsUserFacadeBean<L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
							extends UtilsFacadeBean
							implements UtilsUserFacade<L,D,C,R,V,U,A,USER>
{	
	public UtilsUserFacadeBean(EntityManager em)
	{
		super(em);
	}
	
	@Override public USER load(Class<USER> cUser, USER user)
	{
		user = em.find(cUser, user.getId());
		user.getRoles().size();
		return user;
	}

	@Override public  List<USER> likeNameFirstLast(Class<USER> cUser, String query)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<USER> criteriaQuery = cB.createQuery(cUser);
	    
	    Root<USER> fromType = criteriaQuery.from(cUser);
	    
	    Expression<String> literal = cB.upper(cB.literal("%"+query+"%"));
	    Expression<String> eFirst = fromType.get("firstName");
	    Expression<String> eLast = fromType.get("lastName");
	    
	    CriteriaQuery<USER> select = criteriaQuery.select(fromType);
	    select.where(cB.or(cB.like(cB.upper(eFirst),literal),cB.like(cB.upper(eLast),literal)));
	    
	    TypedQuery<USER> q = em.createQuery(select);
		return q.getResultList();
	}
}