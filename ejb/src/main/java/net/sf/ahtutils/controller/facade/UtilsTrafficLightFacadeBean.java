package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;

import net.sf.ahtutils.interfaces.facade.UtilsTrafficLightFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class UtilsTrafficLightFacadeBean <L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>,LIGHT extends JeeslTrafficLight<L,D,SCOPE>>
	extends UtilsFacadeBean implements UtilsTrafficLightFacade<L,D,SCOPE,LIGHT>
{		
	public UtilsTrafficLightFacadeBean(EntityManager em)
	{
		super(em);
	}

	public List<LIGHT> allOrderedTrafficLights(Class<LIGHT> cLight,SCOPE scope)
	{
		return this.allOrderedParent(cLight, "threshold", true, "scope", scope);
	}
}