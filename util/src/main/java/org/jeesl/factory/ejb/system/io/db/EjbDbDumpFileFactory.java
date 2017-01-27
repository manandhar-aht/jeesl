package org.jeesl.factory.ejb.system.io.db;

import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbDbDumpFileFactory<L extends UtilsLang,D extends UtilsDescription,
								DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
								FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
								HOST extends UtilsStatus<HOST,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDbDumpFileFactory.class);
	
	private final Class<FILE> cFile;
    
	public EjbDbDumpFileFactory(final Class<FILE> cFile)
	{       
        this.cFile = cFile;
	}
	
	public FILE build(DUMP dump, HOST host, STATUS status)
	{
		FILE ejb = null;
		try
		{
			 ejb = cFile.newInstance();
			 ejb.setDump(dump);
			 ejb.setHost(host);
			 ejb.setStatus(status);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}