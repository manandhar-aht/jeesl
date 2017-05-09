package org.jeesl.controller.facade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.util.JeeslGraphicFacade;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslGraphicFacadeBean<L extends UtilsLang, D extends UtilsDescription,
									S extends EjbWithId,
									G extends JeeslGraphic<L,D,G,GT,FS>,
									GT extends UtilsStatus<GT,L,D>,
									FS extends UtilsStatus<FS,L,D>>
					extends UtilsFacadeBean
					implements JeeslGraphicFacade<L,D,S,G,GT,FS>
{	
	private final Class<S> cStatus;
	private final Class<G> cG;
	
	public JeeslGraphicFacadeBean(EntityManager em, final Class<S> cStatus, final Class<G> cG)
	{
		super(em);
		this.cStatus=cStatus;
		this.cG=cG;
	}

	@Override
	public G fGraphicForStatus(long statusId) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		
		CriteriaQuery<G> cQ = cB.createQuery(cG);
		Root<S> monitoring = cQ.from(cStatus);
		
		Path<G> pathProject = monitoring.get("graphic");
		Path<Long> pId = monitoring.get("id");
		
		cQ.where(cB.equal(pId,statusId));
		cQ.select(pathProject);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No Graphic found for status.id"+statusId);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Multiple Results for status.id"+statusId);}
	}
}