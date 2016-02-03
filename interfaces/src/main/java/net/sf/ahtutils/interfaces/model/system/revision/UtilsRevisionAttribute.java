package net.sf.ahtutils.interfaces.model.system.revision;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsRevisionAttribute<L extends UtilsLang,D extends UtilsDescription,
										RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
										RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
										RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
										RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
										RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	RE getEntity();
	void setEntity(RE entitiy);
	
	boolean getOutdated();
	void setOutdated(boolean outdated);
}