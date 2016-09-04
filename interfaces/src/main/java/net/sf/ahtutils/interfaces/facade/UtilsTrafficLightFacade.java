package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface UtilsTrafficLightFacade <L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>,LIGHT extends JeeslTrafficLight<L,D,SCOPE>>
					extends UtilsFacade
{		
	List<LIGHT> allOrderedTrafficLights(Class<LIGHT> cLight,SCOPE scope);
}
