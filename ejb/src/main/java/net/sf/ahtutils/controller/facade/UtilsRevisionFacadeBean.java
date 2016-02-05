package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class UtilsRevisionFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,	
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
					extends UtilsFacadeBean
					implements UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>
{	
	public UtilsRevisionFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override public RE load(Class<RE> cEntity, RE entity)
	{
		entity = em.find(cEntity, entity.getId());
		entity.getAttributes().size();
		entity.getMaps().size();
		return entity;
	}
	
	@Override public RV load(Class<RV> cView, RV view)
	{
		view = em.find(cView, view.getId());
		view.getMaps().size();
		return view;
	}

	@Override
	public void rm(Class<RVM> cMappingView, RVM mapping) throws UtilsConstraintViolationException
	{
		mapping = em.find(cMappingView, mapping.getId());
		mapping.getView().getMaps().remove(mapping);
		this.rmProtected(mapping);
	}
}