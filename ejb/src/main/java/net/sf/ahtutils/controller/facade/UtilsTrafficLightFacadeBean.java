package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.system.graphic.JeeslTrafficLightFacade;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class UtilsTrafficLightFacadeBean <L extends UtilsLang,D extends UtilsDescription,
											LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
											SCOPE extends UtilsStatus<SCOPE,L,D>>
	extends UtilsFacadeBean implements JeeslTrafficLightFacade<L,D,LIGHT,SCOPE>
{	
	private final Class<LIGHT> cLight;
	
	public UtilsTrafficLightFacadeBean(EntityManager em, final Class<LIGHT> cLight)
	{
		super(em);
		this.cLight=cLight;
	}

	public List<LIGHT> allOrderedTrafficLights(SCOPE scope)
	{
		return this.allOrderedParent(cLight, "threshold", true, "scope", scope);
	}
	
	public List<LIGHT> allOrderedTrafficLights()
	{
		return this.allOrdered(cLight, "threshold", true);
	}
}