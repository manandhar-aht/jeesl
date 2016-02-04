package net.sf.ahtutils.interfaces.facade;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public interface UtilsRevisionFacade <L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,RA>>
			extends UtilsFacade
{	
	RE load(Class<RE> cEntity, RE entity);
}