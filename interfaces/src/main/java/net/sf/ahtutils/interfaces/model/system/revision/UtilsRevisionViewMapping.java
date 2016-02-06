package net.sf.ahtutils.interfaces.model.system.revision;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionViewMapping<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
		extends EjbWithId,EjbSaveable,
				EjbWithPositionVisible
{					
	RV getView();
	void setView(RV view);
	
	RS getScope();
	void setScope(RS scope);
	
	RE getEntity();
	void setEntity(RE entity);
	
	String getXpath();
	void setXpath(String xpath);
}