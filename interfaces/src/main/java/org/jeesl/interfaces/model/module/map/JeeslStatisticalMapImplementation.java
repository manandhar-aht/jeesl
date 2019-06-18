package org.jeesl.interfaces.model.module.map;

import java.io.Serializable;

import org.jeesl.interfaces.model.with.status.JeeslWithStatus;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslStatisticalMapImplementation<MAP extends JeeslStatisticalMap<?,?>,
													STATUS extends JeeslStatisticMapStatus<?,?,STATUS,?>,
													LEVEL extends JeeslLocationLevel<?,?,LEVEL,?>>
						extends Serializable,EjbSaveable,
								EjbWithId,
								JeeslWithStatus<STATUS>
{	
	public enum Attributes{}
	
	MAP getMap();
	void setMap(MAP map);
	
	LEVEL getLevel();
	void setLevel(LEVEL level);
}