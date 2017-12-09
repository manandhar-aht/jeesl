package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoDmsFacade <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
									DMS extends JeeslIoDms<L,D,STORAGE,AS,S>,
									STORAGE extends JeeslFileStorage<L,D,?>,
									AS extends JeeslAttributeSet<L,D,?,?>,
									S extends JeeslIoDmsSection<L,S>>
						extends UtilsFacade
{
	S load(S section, boolean recursive);
}