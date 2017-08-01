package org.jeesl.interfaces.model.system.io.cms;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCms<L extends UtilsLang,D extends UtilsDescription,
								CMS extends JeeslIoCms<L,D,CMS,V>,
								V extends JeeslIoCmsVisiblity<L,D,CMS,V>
								>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPosition,EjbWithLang<L>
{		
	
}