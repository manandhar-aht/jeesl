package org.jeesl.interfaces.model.system.io.cms;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslIoCmsVisiblity<L extends UtilsLang,D extends UtilsDescription,
										C extends UtilsStatus<C,L,D>,
										CMS extends JeeslIoCms<L,D,C,CMS,V,S,E,T>,
										V extends JeeslIoCmsVisiblity<L,D,C,CMS,V,S,E,T>,
										S extends JeeslIoCmsSection<L,D,C,CMS,V,S,E,T>,
										E extends JeeslIoCmsElement<L,D,C,CMS,V,S,E,T>,
										T extends UtilsStatus<T,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithVisible
{	
		
}