package org.jeesl.web.mbean.prototype.admin.system.io.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.facade.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractAdminDbDumpBean <L extends UtilsLang,D extends UtilsDescription,
									DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
									FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
									HOST extends UtilsStatus<HOST,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbDumpBean.class);
	
	private JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb;
	
	private List<DUMP> dumps; public List<DUMP> getDumps(){return dumps;}
	private List<HOST> hosts; public List<HOST> getHosts() {return hosts;}
	private Map<DUMP,Map<HOST,FILE>> mapFiles; public Map<DUMP, Map<HOST, FILE>> getMapFiles() {return mapFiles;}
	
	private Class<DUMP> cDump;
	private Class<FILE> cFile;
	private Class<HOST> cHost;
	
	public void initSuper(JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb, final Class<DUMP> cDump, final Class<FILE> cFile, final Class<HOST> cHost, final Class<STATUS> cStatus)
	{
		this.fDb=fDb;
		this.cDump=cDump;
		this.cFile=cFile;
		this.cHost=cHost;
		refreshList();
	}
	
	protected void refreshList()
	{
		dumps = fDb.allOrdered(cDump,JeeslDbDump.Attributes.record.toString(),false);
		hosts = fDb.allOrderedPositionVisible(cHost);
		
		mapFiles = new HashMap<DUMP,Map<HOST,FILE>>();
		for(DUMP d : dumps)
		{
			mapFiles.put(d,new HashMap<HOST,FILE>());
		}
		
		List<FILE> files = fDb.all(cFile);
		for(FILE f : files)
		{
			mapFiles.get(f.getDump()).put(f.getHost(),f);
		}
		
		logger.info(cDump.getSimpleName()+": "+dumps.size());
		logger.info(cHost.getSimpleName()+": "+hosts.size());
		logger.info(cFile.getSimpleName()+": "+files.size());
	}
}