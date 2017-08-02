package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoCmsFacade <L extends UtilsLang,D extends UtilsDescription,
									C extends UtilsStatus<C,L,D>,
									CMS extends JeeslIoCms<L,D,C,CMS,V,S,E,T>,
									V extends JeeslIoCmsVisiblity<L,D,C,CMS,V,S,E,T>,
									S extends JeeslIoCmsSection<L,D,C,CMS,V,S,E,T>,
									E extends JeeslIoCmsElement<L,D,C,CMS,V,S,E,T>,
									T extends UtilsStatus<T,L,D>>
						extends UtilsFacade
{

}