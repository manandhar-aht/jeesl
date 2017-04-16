package org.jeesl.interfaces.model.module.logframe;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslLogFrameArea<L extends UtilsLang, D extends UtilsDescription,
									AREA extends JeeslLogFrameArea<L,D,AREA,IT>,
									IT extends UtilsStatus<IT,L,D>
									>		
{
	IT getType();
	void setType(IT type);
}