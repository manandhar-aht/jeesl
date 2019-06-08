package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFileFactory;
import org.jeesl.factory.ejb.system.io.db.EjbIoDumpFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class IoDbFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								DUMP extends JeeslDbDump<FILE>,
								FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
								HOST extends JeeslDbHost<HOST,L,D,?>,
								STATUS extends JeeslDbDumpStatus<STATUS,L,D,?>>
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
	
	public EjbIoDumpFactory<DUMP> dump(){return new EjbIoDumpFactory<>(cDump);}
	public EjbDbDumpFileFactory<DUMP,FILE,HOST,STATUS> file(){return new EjbDbDumpFileFactory<>(cFile);}
}