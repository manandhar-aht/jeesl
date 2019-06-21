package org.jeesl.web.mbean.prototype.system.io.db;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.factory.builder.io.IoDbFactoryBuilder;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpHost;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.replication.JsonPostgresReplication;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractDbReplicationBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem,
									DUMP extends JeeslDbDump<SYSTEM,FILE>,
									FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
									HOST extends JeeslDbDumpHost<HOST,L,D,?>,
									STATUS extends JeeslDbDumpStatus<L,D,STATUS,?>>
						extends AbstractAdminBean<L,D>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDbReplicationBean.class);
	
	private JeeslIoDbFacade<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fDb;
	private final IoDbFactoryBuilder<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fbDb;
	
//	private SbDateHandler sbDateHandler; public SbDateHandler getSbDateHandler() {return sbDateHandler;}
//	protected Chart chart; public Chart getChart() {return chart;}

	private List<JsonPostgresReplication> replications; public List<JsonPostgresReplication> getReplications() {return replications;}
	
	public AbstractDbReplicationBean(final IoDbFactoryBuilder<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fbDb)
	{
		super(fbDb.getClassL(),fbDb.getClassD());
		this.fbDb=fbDb;
//		sbDateHandler = new SbDateHandler(this);
//		sbDateHandler.setEnforceStartOfDay(true);
//		sbDateHandler.initWeeksToNow(2);
	}
	
	public void postConstructDbReplication(JeeslIoDbFacade<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fDb)
	{
		this.fDb=fDb;
		refreshList(); 
	}
	
	protected void refreshList()
	{		
		replications = fDb.postgresReplicationInfo();
	}
}