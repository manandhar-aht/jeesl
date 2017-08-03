package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoCmsFacade <L extends UtilsLang,D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
									V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
									S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
									E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
									T extends UtilsStatus<T,L,D>,
									C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
									M extends UtilsStatus<M,L,D>>
						extends UtilsFacade
{
	S load(S section, boolean recursive);
}