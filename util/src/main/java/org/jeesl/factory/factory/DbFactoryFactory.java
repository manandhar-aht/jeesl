package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.io.db.EjbIoDumpFactory;
import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFileFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class DbFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
							DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
							FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
							HOST extends UtilsStatus<HOST,L,D>,
							STATUS extends UtilsStatus<STATUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(DbFactoryFactory.class);
	
//	private final Class<L> cL;
//	private final Class<D> cD;
	private final Class<DUMP> cDump;
	private final Class<FILE> cFile;
//	private final Class<HOST> cHost;
//	private final Class<STATUS> cStatus;
    
	private DbFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<DUMP> cDump, final Class<FILE> cFile, final Class<HOST> cHost, final Class<STATUS> cStatus)
	{       
//		this.cL = cL;
//       this.cD = cD;
        this.cDump = cDump;
        this.cFile = cFile;
//        this.cHost=cHost;
//        this.cStatus = cStatus;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
					FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
					HOST extends UtilsStatus<HOST,L,D>,
					STATUS extends UtilsStatus<STATUS,L,D>>
		DbFactoryFactory<L,D,DUMP,FILE,HOST,STATUS> factory(final Class<L> cL,final Class<D> cD,final Class<DUMP> cDump, final Class<FILE> cFile, final Class<HOST> cHost, final Class<STATUS> cStatus)
	{
		return new DbFactoryFactory<L,D,DUMP,FILE,HOST,STATUS>(cL,cD,cDump,cFile,cHost,cStatus);
	}
	
	public EjbIoDumpFactory<L,D,DUMP,FILE,HOST,STATUS> dump()
	{
		return new EjbIoDumpFactory<L,D,DUMP,FILE,HOST,STATUS>(cDump);
	}
	
	public EjbDbDumpFileFactory<L,D,DUMP,FILE,HOST,STATUS> file()
	{
		return new EjbDbDumpFileFactory<L,D,DUMP,FILE,HOST,STATUS>(cFile);
	}
}