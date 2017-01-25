package org.jeesl.interfaces.model.system.io.db;

import net.sf.ahtutils.interfaces.model.date.EjbWithDateRange;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslDbDumpFile<L extends UtilsLang,D extends UtilsDescription,
								HOST extends UtilsStatus<HOST,L,D>,
								DUMP extends JeeslDbDumpFile<L,D,HOST,DUMP,STATUS>,
								STATUS extends UtilsStatus<STATUS,L,D>>
					extends EjbWithId,EjbWithDateRange,EjbWithName
{
	public static enum Status{stored,flagged,deleted};
	
	long getSize();
	void setSize(long size);
	
	boolean isDeleteable();
	void setDeleteable(boolean deleteable);
}