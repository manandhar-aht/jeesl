package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoDmsFacade <L extends UtilsLang,D extends UtilsDescription,
									DMS extends JeeslIoDms<L,D,AS,S>,
									AS extends JeeslAttributeSet<L,D,?,?>,
									S extends JeeslIoDmsSection<L,S>>
						extends UtilsFacade
{
	S load(S section, boolean recursive);
}