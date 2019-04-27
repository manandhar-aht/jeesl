package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFileContainer<STORAGE extends JeeslFileStorage<?,?,?>, META extends JeeslFileMeta<?,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable
{
	public enum Attributes{storage}
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
	
	List<META> getMetas();
	void setMetas(List<META> metas);
}