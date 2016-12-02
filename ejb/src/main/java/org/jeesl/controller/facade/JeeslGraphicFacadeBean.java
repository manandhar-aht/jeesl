package org.jeesl.controller.facade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.interfaces.facade.JeeslGraphicFacade;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslGraphicFacadeBean<L extends UtilsLang,
									D extends UtilsDescription,
									G extends UtilsGraphic<L,D,G,GT,GS>,
									GT extends UtilsStatus<GT,L,D>,
									GS extends UtilsStatus<GS,L,D>>
					extends UtilsFacadeBean
					implements JeeslGraphicFacade<L,D,G,GT,GS>
{	
	private final Class<G> cG;
	
	public JeeslGraphicFacadeBean(EntityManager em, final Class<G> cG)
	{
		super(em);
		this.cG=cG;
	}

	@Override
	public <T extends EjbWithId> G fGraphic(Class<T> c, long statusId) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		
		CriteriaQuery<G> cQ = cB.createQuery(cG);
		Root<T> monitoring = cQ.from(c);
		
		Path<G> pathProject = monitoring.get("graphic");
		Path<Long> pId = monitoring.get("id");
		
		cQ.where(cB.equal(pId,statusId));
		cQ.select(pathProject);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No Graphic found for status.id"+statusId);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Multiple Results for status.id"+statusId);}
	}
}