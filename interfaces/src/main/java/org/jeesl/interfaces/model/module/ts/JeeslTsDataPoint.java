package org.jeesl.interfaces.model.module.ts;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTsDataPoint <DATA extends JeeslTsData<?,?,?,?>>
		extends EjbWithId,EjbSaveable,Serializable,EjbRemoveable,EjbPersistable
{
	
	DATA getData();
	void setData(DATA data);
	
	Double getValue();
	void setValue(Double value);
}