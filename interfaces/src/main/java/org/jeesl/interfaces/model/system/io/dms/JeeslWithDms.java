package org.jeesl.interfaces.model.system.io.dms;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithDms<DMS extends JeeslIoDms<?,?,?,?,?,?>>
						extends EjbWithId
{
	public enum Attributes {dms}
	
	DMS getDms();
	void setDms(DMS cms);
}