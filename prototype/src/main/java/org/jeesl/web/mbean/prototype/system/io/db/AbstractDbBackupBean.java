package org.jeesl.web.mbean.prototype.system.io.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.jeesl.controller.handler.sb.SbDateHandler;
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

public class AbstractDbBackupBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
										DUMP extends JeeslDbDump<FILE>,
										FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
										HOST extends JeeslDbHost<HOST,L,D,?>,
										STATUS extends JeeslDbDumpStatus<STATUS,L,D,?>>
						implements Serializable,SbDateIntervalSelection
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDbBackupBean.class);
	
	private JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb;
	private final IoDbFactoryBuilder<L,D,DUMP,FILE,HOST,STATUS> fbDb;
	
	private SbDateHandler sbDateHandler; public SbDateHandler getSbDateHandler() {return sbDateHandler;}
	
	private List<DUMP> dumps; public List<DUMP> getDumps(){return dumps;}
	private List<HOST> hosts; public List<HOST> getHosts() {return hosts;}
	private Map<DUMP,Map<HOST,FILE>> mapFiles; public Map<DUMP, Map<HOST, FILE>> getMapFiles() {return mapFiles;}
	
	public AbstractDbBackupBean(final IoDbFactoryBuilder<L,D,DUMP,FILE,HOST,STATUS> fbDb)
	{
		this.fbDb=fbDb;
		sbDateHandler = new SbDateHandler(this);
		sbDateHandler.setEnforceStartOfDay(true);
		sbDateHandler.initWeeksToNow(2);
	}
	
	public void postConstructDbBackup(JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb)
	{
		this.fDb=fDb;
		hosts = fDb.allOrderedPositionVisible(fbDb.getClassHost());
		refreshList(); 
	}
	
	@Override public void callbackDateChanged()
	{
		refreshList();
	}
	
	protected void refreshList()
	{
		dumps = fDb.inInterval(fbDb.getClassDump(),sbDateHandler.getDate1(),sbDateHandler.getDate2());
		
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