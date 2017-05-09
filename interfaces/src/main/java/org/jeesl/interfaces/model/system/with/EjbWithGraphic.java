package org.jeesl.interfaces.model.system.with;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface EjbWithGraphic<L extends UtilsLang,
								D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,FS>,
								GT extends UtilsStatus<GT,L,D>,
								FS extends UtilsStatus<FS,L,D>>
{
	G getGraphic();
	void setGraphic(G graphic) ;
}