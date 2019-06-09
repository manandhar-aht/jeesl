package org.jeesl.factory.ejb.system.io.db;

import java.util.Date;

import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoDumpFactory<SYSTEM extends JeeslIoSsiSystem,
								DUMP extends JeeslDbDump<SYSTEM,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoDumpFactory.class);
	
	private final Class<DUMP> cDumpFile;
    
	public EjbIoDumpFactory(final Class<DUMP> cDumpFile)
	{       
        this.cDumpFile = cDumpFile;
	}
	
	public DUMP build(net.sf.exlp.xml.io.File file)
	{
		return build(file.getName(),file.getSize(),file.getLastModifed().toGregorianCalendar().getTime());
    }
	
	public DUMP build(String name, long size, Date record)
	{
		DUMP ejb = null;
		try
		{
			 ejb = cDumpFile.newInstance();
			 ejb.setName(name);
			 ejb.setSize(size);
			 ejb.setRecord(record);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}