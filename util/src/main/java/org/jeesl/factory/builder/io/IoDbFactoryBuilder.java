package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFileFactory;
import org.jeesl.factory.ejb.system.io.db.EjbIoDumpFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoDbFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
								FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
								HOST extends UtilsStatus<HOST,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>>
			extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoDbFactoryBuilder.class);
	
	private final Class<DUMP> cDump; public Class<DUMP> getClassDump(){return cDump;}
	private final Class<FILE> cFile; public Class<FILE> getClassFile(){return cFile;}
	private final Class<HOST> cHost; public Class<HOST> getClassHost(){return cHost;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus(){return cStatus;}
	
	public IoDbFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<DUMP> cDump, final Class<FILE> cFile, final Class<HOST> cHost, final Class<STATUS> cStatus)
	{
		super(cL,cD);
		this.cDump = cDump;
		this.cFile=cFile;
		this.cHost=cHost;
		this.cStatus=cStatus;
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