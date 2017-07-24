package org.jeesl.api.facade.system.graphic;

import java.util.List;

import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTrafficLightFacade <L extends UtilsLang,D extends UtilsDescription,
											LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
											SCOPE extends UtilsStatus<SCOPE,L,D>>
					extends UtilsFacade
{		
	List<LIGHT> allOrderedTrafficLights(Class<LIGHT> cLight,SCOPE scope);
}
