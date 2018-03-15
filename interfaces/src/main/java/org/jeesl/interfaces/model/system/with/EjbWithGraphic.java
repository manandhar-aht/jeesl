package org.jeesl.interfaces.model.system.with;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;

public interface EjbWithGraphic<G extends JeeslGraphic<?,?,G,?,?,?>>
{
	G getGraphic();
	void setGraphic(G graphic) ;
}