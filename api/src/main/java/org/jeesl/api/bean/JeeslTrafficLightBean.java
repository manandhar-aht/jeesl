package org.jeesl.api.bean;

import java.util.List;

import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTrafficLightBean<L extends UtilsLang,D extends UtilsDescription,
										LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
										SCOPE extends UtilsStatus<SCOPE,L,D>>
{	
	List<LIGHT> getTrafficLights(String scope);
	
	void refreshTrafficLights();
}