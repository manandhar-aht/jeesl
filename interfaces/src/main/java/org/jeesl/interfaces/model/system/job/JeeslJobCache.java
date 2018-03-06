package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslJobCache<TEMPLATE extends JeeslJobTemplate<?,?,?,?,?>, CONTAINER extends JeeslFileContainer<?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithNonUniqueCode,EjbWithRecord
{
	public static enum Attributes{template,code};
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	byte[] getData();
	void setData(byte[] data);
}