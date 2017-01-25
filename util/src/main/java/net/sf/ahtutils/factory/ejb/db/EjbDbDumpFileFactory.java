package net.sf.ahtutils.factory.ejb.db;

import java.io.File;
import java.util.Date;

import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbDbDumpFileFactory<L extends UtilsLang,D extends UtilsDescription,
									HOST extends UtilsStatus<HOST,L,D>,
									DUMP extends JeeslDbDumpFile<L,D,HOST,DUMP,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDbDumpFileFactory.class);
	
	private final Class<DUMP> cDumpFile;
    
	public EjbDbDumpFileFactory(final Class<DUMP> cDumpFile)
	{       
        this.cDumpFile = cDumpFile;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					HOST extends UtilsStatus<HOST,L,D>,
					DUMP extends JeeslDbDumpFile<L,D,HOST,DUMP,STATUS>,
					STATUS extends UtilsStatus<STATUS,L,D>>
	EjbDbDumpFileFactory<L,D,HOST,DUMP,STATUS> factory(final Class<DUMP> cDumpFile)
	{
		return new EjbDbDumpFileFactory<L,D,HOST,DUMP,STATUS>(cDumpFile);
	}
    
	public DUMP build(net.sf.exlp.xml.io.File file)
	{
		return build(file.getName(),file.getSize(),file.getLastModifed().toGregorianCalendar().getTime());
    }
	
	public DUMP build(File file)
	{
		return build(file.getName(),file.length(),new Date(file.lastModified()));
    }
	
	public DUMP build(String name, long size, Date record)
	{
		DUMP ejb = null;
		try
		{
			 ejb = cDumpFile.newInstance();
			 ejb.setName(name);
			 ejb.setSize(size);
			 ejb.setStartDate(record);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}