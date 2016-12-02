package org.jeesl.controller.facade;

import javax.persistence.EntityManager;

import org.jeesl.interfaces.facade.JeeslGraphicFacade;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

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
}