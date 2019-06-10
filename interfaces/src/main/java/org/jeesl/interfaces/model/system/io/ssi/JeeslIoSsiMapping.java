package org.jeesl.interfaces.model.system.io.ssi;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiMapping <SYSTEM extends JeeslIoSsiSystem,
									ENTITY extends JeeslRevisionEntity<?,?,?,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable
{	
	public enum Attributes {entity,json}
	
	public SYSTEM getSystem();
	public void setSystem(SYSTEM system);
	
	public ENTITY getEntity();
	public void setEntity(ENTITY entity);
	
	public ENTITY getJson();
	public void setJson(ENTITY json);
}