package net.sf.ahtutils.interfaces.model.graphic;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface UtilsWithGraphic<L extends UtilsLang,
								D extends UtilsDescription,
								G extends UtilsGraphic<L,D,GT,GS>,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>>
{
	G getGraphic();
	void setGraphic(G graphic) ;
}
