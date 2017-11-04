package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;

public interface JeeslRevisionEntityMapping<RS extends JeeslRevisionScope<?,?,?,?,?,RS,RST,RE,?,?,?>,
											RST extends UtilsStatus<RST,?,?>,
											RE extends JeeslRevisionEntity<?,?,?,?,?>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible
{			
	public static enum Type{xpath,jpqlTree}
	
	RS getScope();
	void setScope(RS scope);
	
	RST getType();
	void setType(RST type);
	
	RE getEntity();
	void setEntity(RE entity);
	
	String getXpath();
	void setXpath(String xpath);
	
	String getJpqlTree();
	void setJpqlTree(String jpqlTree);
}