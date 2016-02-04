package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.interfaces.facade.UtilsGraphicFacade;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class UtilsGraphicFacadeBean<L extends UtilsLang,
									D extends UtilsDescription,
									G extends UtilsGraphic<L,D,G,GT,GS>,
									GT extends UtilsStatus<GT,L,D>,
									GS extends UtilsStatus<GS,L,D>>
					extends UtilsFacadeBean
					implements UtilsGraphicFacade<L,D,G,GT,GS>
{	
	public UtilsGraphicFacadeBean(EntityManager em)
	{
		super(em);
	}
}