package org.jeesl.api.facade.io;

import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpHost;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.model.json.db.tuple.replication.JsonPostgresReplication;
import org.openfuxml.content.table.Table;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoDbFacade <L extends UtilsLang,D extends UtilsDescription,
								SYSTEM extends JeeslIoSsiSystem,
								DUMP extends JeeslDbDump<SYSTEM,DF>,
								DF extends JeeslDbDumpFile<DUMP,DH,DS>,
								DH extends JeeslDbDumpHost<L,D,DH,?>,
								DS extends JeeslDbDumpStatus<L,D,DS,?>>
		extends UtilsFacade
{
	List<DF> fDumpFiles(DH host);
	DF fDumpFile(DUMP dump, DH host) throws UtilsNotFoundException;
	
	String version();
	long countExact(Class<?> c);
	Map<Class<?>,Long> count(List<Class<?>> list);
	long countEstimate(Class<?> c);
	
	Table connections(String userName);
	
	List<JsonPostgresReplication> postgresReplicationInfo();
	
	JsonFlatFigures dbConnections(String dbName);
	JsonFlatFigures dbQueries(String dbName);
}