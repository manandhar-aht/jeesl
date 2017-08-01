package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoCmsFacade <L extends UtilsLang,D extends UtilsDescription,
									CMS extends JeeslIoCms<L,D,CMS,V>,
									V extends JeeslIoCmsVisiblity<L,D,CMS,V>
									>
						extends UtilsFacade
{

}