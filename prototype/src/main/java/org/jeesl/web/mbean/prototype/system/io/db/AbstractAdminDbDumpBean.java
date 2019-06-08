package org.jeesl.web.mbean.prototype.system.io.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.factory.builder.io.IoDbFactoryBuilder;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractAdminDbDumpBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
										DUMP extends JeeslDbDump<FILE>,
										FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
										HOST extends JeeslDbHost<HOST,L,D,?>,
										STATUS extends JeeslDbDumpStatus<STATUS,L,D,?>>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbDumpBean.class);
	
	private JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb;
	private final IoDbFactoryBuilder<L,D,DUMP,FILE,HOST,STATUS> fbDb;
	
	private List<DUMP> dumps; public List<DUMP> getDumps(){return dumps;}
	private List<HOST> hosts; public List<HOST> getHosts() {return hosts;}
	private Map<DUMP,Map<HOST,FILE>> mapFiles; public Map<DUMP, Map<HOST, FILE>> getMapFiles() {return mapFiles;}
	
	public AbstractAdminDbDumpBean(final IoDbFactoryBuilder<L,D,DUMP,FILE,HOST,STATUS> fbDb)
	{
		this.fbDb=fbDb;
	}
	
	public void initSuper(JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb)
	{
		this.fDb=fDb;
		refreshList();
	}
	
	protected void refreshList()
	{
		dumps = fDb.allOrdered(fbDb.getClassDump(),JeeslDbDump.Attributes.record.toString(),false);
		hosts = fDb.allOrderedPositionVisible(fbDb.getClassHost());
		
		mapFiles = new HashMap<DUMP,Map<HOST,FILE>>();
		for(DUMP d : dumps)
		{
			mapFiles.put(d,new HashMap<HOST,FILE>());
		}
		
		List<FILE> files = fDb.all(fbDb.getClassFile());
		for(FILE f : files)
		{
			mapFiles.get(f.getDump()).put(f.getHost(),f);
		}
		
		logger.info(fbDb.getClassDump().getSimpleName()+": "+dumps.size());
		logger.info(fbDb.getClassHost().getSimpleName()+": "+hosts.size());
		logger.info(fbDb.getClassFile().getSimpleName()+": "+files.size());
	}
}