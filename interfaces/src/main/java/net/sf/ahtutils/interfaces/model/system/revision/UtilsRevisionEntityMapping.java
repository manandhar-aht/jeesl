package net.sf.ahtutils.interfaces.model.system.revision;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionEntityMapping<L extends UtilsLang,D extends UtilsDescription,
											RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,REM,RA>,
											RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,REM,RA>,
											RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,REM,RA>,
											RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,REM,RA>,
											REM extends UtilsRevisionEntityMapping<L,D,RV,RVM,RS,RE,REM,RA>,
											RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,REM,RA>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible
{			
	RE getEntity();
	void setEntity(RE entity);
	
	RS getScope();
	void setScope(RS scope);
	
	String getXpath();
	void setXpath(String xpath);
}