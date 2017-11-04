package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;

public interface JeeslRevisionViewMapping<RV extends JeeslRevisionView<?,?,?>,
									RE extends JeeslRevisionEntity<?,?,?,RV,?,?,?,RE,REM,?,?>,
									REM extends JeeslRevisionEntityMapping<?,?,?,RV,?,?,?,RE,REM,?,?>>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithPositionVisible
{					
	RV getView();
	void setView(RV view);
	
	RE getEntity();
	void setEntity(RE entity);
	
	REM getEntityMapping();
	void setEntityMapping(REM entityMapping);
}