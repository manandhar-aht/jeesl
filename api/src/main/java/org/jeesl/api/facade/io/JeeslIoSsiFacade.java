package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoSsiFacade <L extends UtilsLang,D extends UtilsDescription,
									SYSTEM extends JeeslIoSsiSystem,
									MAPPING extends JeeslIoSsiMapping<SYSTEM,?>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends UtilsStatus<LINK,L,D>>
			extends UtilsFacade
{	
	Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping);
}