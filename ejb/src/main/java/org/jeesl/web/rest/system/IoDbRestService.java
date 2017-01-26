package org.jeesl.web.rest.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFactory;
import org.jeesl.interfaces.facade.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.rest.system.io.db.JeeslDbDumpRest;
import org.jeesl.interfaces.rest.system.io.db.JeeslDbRestExport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;

public class IoDbRestService<L extends UtilsLang,D extends UtilsDescription,
							DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
							FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
							HOST extends UtilsStatus<HOST,L,D>,
							STATUS extends UtilsStatus<STATUS,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslDbDumpRest,JeeslDbRestExport
{
	final static Logger logger = LoggerFactory.getLogger(IoDbRestService.class);
	
	private JeeslIoDbFacade fDb;
	
	private final Class<DUMP> cDump;
	private final Class<FILE> cFile;
	private final Class<HOST> cHost;
	private final Class<STATUS> cStatus;
	
//	private EjbDbDumpFactory<L,D,DUMP,FILE,HOST,STATUS> efDump;
	private EjbDbDumpFactory<L,D,DUMP,FILE,HOST,STATUS> efDumpFile;
	private EjbStatusFactory<HOST,L,D> efHost; 
	
	public IoDbRestService(JeeslIoDbFacade fDb,final Class<L> cL, final Class<D> cD,final Class<DUMP> cDump,final Class<FILE> cFile,final Class<HOST> cHost,final Class<STATUS> cStatus)
	{
		super(fDb,cL,cD);
		this.fDb = fDb;
		
		this.cDump=cDump;
		this.cFile=cFile;
		this.cHost=cHost;
		this.cStatus=cStatus;
		
		efHost = EjbStatusFactory.createFactory(cHost,cL,cD);
		
		efDumpFile = EjbDbDumpFactory.factory(cDump);
	}
	
//	@Override public Container exportSystemDbActivityState() {return xfContainer.build(fDb.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoDbDumpStatus() {return xfContainer.build(fDb.allOrderedPosition(cStatus));}
	
	@Override public DataUpdate uploadDumps(Dir directory)
	{
		DataUpdateTracker dut = new DataUpdateTracker();
		
		HOST host;
		try{host = fDb.fByCode(cHost, directory.getCode());}
		catch (UtilsNotFoundException e)
		{
			try{host = fDb.persist(efHost.create(directory.getCode()));}
			catch (UtilsConstraintViolationException e1) {dut.fail(e1, true);return dut.toDataUpdate();}
		}
		
		for(File xFile : directory.getFile())
		{
			DUMP eDump;
			try{eDump = fDb.fByName(cDump, xFile.getName());}
			catch (UtilsNotFoundException e)
			{
//				eDump = fDb.persist(o)
			}
		}
		
		return new DataUpdate();
	}
}