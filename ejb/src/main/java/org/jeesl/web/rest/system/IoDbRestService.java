package org.jeesl.web.rest.system;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.interfaces.facade.JeeslIoDbFacade;
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
import net.sf.ahtutils.factory.ejb.db.EjbDbDumpFileFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.xml.io.Dir;

public class IoDbRestService<L extends UtilsLang,D extends UtilsDescription,
							HOST extends UtilsStatus<HOST,L,D>,
							DUMP extends JeeslDbDumpFile<L,D,HOST,DUMP,STATUS>,
							STATUS extends UtilsStatus<STATUS,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslDbDumpRest,JeeslDbRestExport
{
	final static Logger logger = LoggerFactory.getLogger(IoDbRestService.class);
	
	private JeeslIoDbFacade fDb;
	
	private final Class<DUMP> cDump;
	private final Class<STATUS> cStatus;
	
	private EjbDbDumpFileFactory<L,D,HOST,DUMP,STATUS> fDumpFile;
	
	public IoDbRestService(JeeslIoDbFacade fDb,final Class<L> cL, final Class<D> cD,final Class<DUMP> cDump,final Class<STATUS> cStatus)
	{
		super(fDb,cL,cD);
		this.fDb = fDb;
		
		this.cDump=cDump;
		this.cStatus=cStatus;
		
		fDumpFile = EjbDbDumpFileFactory.factory(cDump);
	}
	
//	@Override public Container exportSystemDbActivityState() {return xfContainer.build(fDb.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoDbDumpStatus() {return xfContainer.build(fDb.allOrderedPosition(cStatus));}
	
	@Override public DataUpdate uploadDumps(Dir directory)
	{
		Set<DUMP> set = new HashSet<DUMP>();
		set.addAll(fDb.all(cDump));
		
		for(net.sf.exlp.xml.io.File f : directory.getFile())
		{
			DUMP ejb;
			try
			{
				ejb = fDb.fByName(cDump,f.getName());
				set.remove(ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = fDumpFile.build(f);
					ejb = fDb.persist(ejb);
				}
				catch (UtilsConstraintViolationException e1) {e1.printStackTrace();}
			}
		}
		logger.info("Size: "+set.size());
		List<DUMP> list = new ArrayList<DUMP>(set);
		for(DUMP ejb : list)
		{
			try
			{
				ejb.setEndDate(new Date());
				fDb.update(ejb);
			}
			catch (UtilsConstraintViolationException e) {e.printStackTrace();}
			catch (UtilsLockingException e) {e.printStackTrace();}
		}
		return new DataUpdate();
	}
	
	public void findDumps2(File fDir)
	{
		Set<DUMP> set = new HashSet<DUMP>();
		set.addAll(fDb.all(cDump));
		
		for(File f : fDir.listFiles())
		{
			DUMP ejb;
			try
			{
				ejb = fDb.fByName(cDump,f.getName());
				set.remove(ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = fDumpFile.build(f);
					ejb = fDb.persist(ejb);
				}
				catch (UtilsConstraintViolationException e1) {e1.printStackTrace();}
			}
		}
		logger.info("Size: "+set.size());
		List<DUMP> list = new ArrayList<DUMP>(set);
		for(DUMP ejb : list)
		{
			try
			{
				ejb.setEndDate(new Date());
				fDb.update(ejb);
			}
			catch (UtilsConstraintViolationException e) {e.printStackTrace();}
			catch (UtilsLockingException e) {e.printStackTrace();}
		}
	}
}