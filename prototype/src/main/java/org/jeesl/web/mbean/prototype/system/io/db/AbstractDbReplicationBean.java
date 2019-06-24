package org.jeesl.web.mbean.prototype.system.io.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.factory.builder.io.IoDbFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbCodeFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbReplicationInfo;
import org.jeesl.interfaces.model.system.io.db.JeeslDbReplicationState;
import org.jeesl.interfaces.model.system.io.db.JeeslDbReplicationSync;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.replication.JsonPostgresConnection;
import org.jeesl.model.json.db.tuple.replication.JsonPostgresReplication;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openfuxml.content.table.Table;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractDbReplicationBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem,
									RI extends JeeslDbReplicationInfo<L,D,RI,?>,
									RS extends JeeslDbReplicationState<L,D,RS,?>,
									RY extends JeeslDbReplicationSync<L,D,RY,?>>
						extends AbstractAdminBean<L,D>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDbReplicationBean.class);
	
	private JeeslIoDbFacade<L,D,SYSTEM,?,?,?,?> fDb;
	private final IoDbFactoryBuilder<L,D,SYSTEM,?,?,?,?,RI,RS,RY> fbDb;
	
	private final Map<String,RI> mapInfo;
	public Map<String, RI> getMapInfo() {
		return mapInfo;
	}

	private final Map<String,RS> mapState;
	private final Map<String,RY> mapSync;
//	protected Chart chart; public Chart getChart() {return chart;}

	private List<JsonPostgresConnection> replications; public List<JsonPostgresConnection> getReplications() {return replications;}
	
	public AbstractDbReplicationBean(final IoDbFactoryBuilder<L,D,SYSTEM,?,?,?,?,RI,RS,RY> fbDb)
	{
		super(fbDb.getClassL(),fbDb.getClassD());
		this.fbDb=fbDb;

		mapInfo = new HashMap<>();
		mapState = new HashMap<>();
		mapSync = new HashMap<>();

	}
	
	public void postConstructDbReplication(JeeslIoDbFacade<L,D,SYSTEM,?,?,?,?> fDb)
	{
		this.fDb=fDb;
		
		mapInfo.putAll(EjbCodeFactory.toMapCode(fDb.allOrderedPositionVisible(fbDb.getClassReplicationInfo())));
		mapState.putAll(EjbCodeFactory.toMapCode(fDb.allOrderedPositionVisible(fbDb.getClassReplicationState())));
		mapSync.putAll(EjbCodeFactory.toMapCode(fDb.allOrderedPositionVisible(fbDb.getClassReplicationSync())));
		
		refreshList(); 
	}
	
	protected void refreshList()
	{		
		//replications = fDb.postgresReplicationInfo();
		replications = fDb.postgresConnections("jeesl");
	}
}