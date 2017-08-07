package org.jeesl.api.facade.io;

import java.util.List;

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
									CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
									V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
									S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
									E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
									T extends UtilsStatus<T,L,D>,
									C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
									M extends UtilsStatus<M,L,D>,
									LOC extends UtilsStatus<LOC,L,D>>
						extends UtilsFacade
{
	S load(S section, boolean recursive);
	List<E> fCmsElements(S section);
}