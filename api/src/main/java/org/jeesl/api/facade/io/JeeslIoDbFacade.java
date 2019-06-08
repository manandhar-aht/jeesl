package org.jeesl.api.facade.io;

import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbHost;
import org.jeesl.model.json.JsonFlatFigures;
import org.openfuxml.content.table.Table;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoDbFacade <L extends UtilsLang,D extends UtilsDescription,
								DUMP extends JeeslDbDump<FILE>,
								FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
								HOST extends JeeslDbHost<HOST,L,D,?>,
								STATUS extends JeeslDbDumpStatus<STATUS,L,D,?>>
		extends UtilsFacade
{
	List<FILE> fDumpFiles(HOST host);
	FILE fDumpFile(DUMP dump, HOST host) throws UtilsNotFoundException;
	
	String version();
	long countExact(Class<?> c);
	Map<Class<?>,Long> count(List<Class<?>> list);
	long countEstimate(Class<?> c);
	
	Table connections(String userName);
	
	JsonFlatFigures dbConnections(String dbName);
	JsonFlatFigures dbQueries(String dbName);
}