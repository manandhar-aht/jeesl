package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsDocument;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsView;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoDmsFacade <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
									DMS extends JeeslIoDms<L,D,STORAGE,AS,DS,S>,
									STORAGE extends JeeslFileStorage<L,D,?>,
									AS extends JeeslAttributeSet<L,D,?,?>,
									DS extends JeeslDomainSet<L,D,?>,
									S extends JeeslIoDmsSection<L,D,S>,
									FILE extends JeeslIoDmsDocument<L,S,FC,AC>,
									VIEW extends JeeslIoDmsView<L,D,DMS>,
									FC extends JeeslFileContainer<?,?>,
									AC extends JeeslAttributeContainer<?,?>>
						extends UtilsFacade
{
	S load(S section, boolean recursive);
}