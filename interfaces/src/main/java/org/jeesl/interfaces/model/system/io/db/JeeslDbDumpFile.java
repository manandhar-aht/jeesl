package org.jeesl.interfaces.model.system.io.db;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDbDumpFile<DUMP extends JeeslDbDump<?>,
								HOST extends JeeslDbHost<HOST,?,?,?>,
								STATUS extends JeeslDbDumpStatus<STATUS,?,?,?>>
					extends Serializable,EjbSaveable,EjbRemoveable,EjbWithId
{
	public static enum Attributes{dump,host,status}
	public static enum Status{stored,flagged,deleted};
	
	DUMP getDump();
	void setDump(DUMP dump);
	
	HOST getHost();
	void setHost(HOST host);
	
	STATUS getStatus();
	void setStatus(STATUS status);
}