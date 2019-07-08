package org.jeesl.interfaces.model.system.with;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithGraphic<G extends JeeslGraphic<?,?,?,?,?>> extends EjbWithId
{
	G getGraphic();
	void setGraphic(G graphic) ;
}