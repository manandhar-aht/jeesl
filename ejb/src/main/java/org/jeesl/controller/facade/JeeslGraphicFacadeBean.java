package org.jeesl.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.system.graphic.JeeslGraphicFacade;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslGraphicFacadeBean<L extends UtilsLang, D extends UtilsDescription,
									S extends EjbWithId,
									G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
					extends UtilsFacadeBean
					implements JeeslGraphicFacade<L,D,S,G,GT,F,FS>
{	
	private final Class<S> cStatus;
	private final Class<G> cG;
	
	public JeeslGraphicFacadeBean(EntityManager em, final Class<S> cStatus, final Class<G> cG)
	{
		super(em);
		this.cStatus=cStatus;
		this.cG=cG;
	}

	@Override public G fGraphicForStatus(long statusId) throws UtilsNotFoundException
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
	
	@Override public <W extends EjbWithGraphic<G>> G fGraphic(Class<W> c, long id) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<G> cQ = cB.createQuery(cG);
		Root<W> w = cQ.from(c);
		
		Path<G> pGraphic = w.get("graphic");
		Path<Long> pId = w.get("id");
		
		cQ.where(cB.equal(pId,id));
		cQ.select(pGraphic);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No Graphic found for status.id"+id);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Multiple Results for status.id"+id);}
	}

	@Override public <T extends EjbWithGraphic<G>> List<T> allWithGraphicFigures(Class<T> c)
	{
		List<T> list = this.all(c);
		for(T ejb : list)
		{
			ejb.getGraphic().getId();
			ejb.getGraphic().getFigures().size();
		}
		return this.all(c);
	}
}