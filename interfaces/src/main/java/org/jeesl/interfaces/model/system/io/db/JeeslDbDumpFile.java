package org.jeesl.interfaces.model.system.io.db;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDbDumpFile<L extends UtilsLang,D extends UtilsDescription,
								DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
								FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
								HOST extends UtilsStatus<HOST,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>>
					extends EjbWithId
{
	public static enum Status{stored,flagged,deleted};
	
	DUMP getDump();
	void setDump(DUMP dump);
	
	HOST getHost();
	void setHost(HOST host);
	
	STATUS getStatus();
	void setStatus(STATUS status);
}