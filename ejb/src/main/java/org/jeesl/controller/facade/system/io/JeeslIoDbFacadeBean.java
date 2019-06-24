
package org.jeesl.controller.facade.system.io;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.factory.builder.io.IoDbFactoryBuilder;
import org.jeesl.factory.json.system.io.db.JsonDbPgStatConnectionFactory;
import org.jeesl.factory.json.system.io.db.JsonDbPgStatQueryFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFiguresFactory;
import org.jeesl.factory.sql.system.db.SqlDbPgStatFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpHost;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.model.json.db.tuple.replication.JsonPostgresConnection;
import org.jeesl.model.json.db.tuple.replication.JsonPostgresReplication;
import org.jsoup.helper.StringUtil;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class JeeslIoDbFacadeBean <L extends UtilsLang,D extends UtilsDescription,
								SYSTEM extends JeeslIoSsiSystem,
								DUMP extends JeeslDbDump<SYSTEM,DF>,
								DF extends JeeslDbDumpFile<DUMP,DH,DS>,
								DH extends JeeslDbDumpHost<L,D,DH,?>,
								DS extends JeeslDbDumpStatus<L,D,DS,?>>
		extends UtilsFacadeBean implements JeeslIoDbFacade<L,D,SYSTEM,DUMP,DF,DH,DS>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslIoDbFacadeBean.class);
	
	private final IoDbFactoryBuilder<L,D,SYSTEM,DUMP,DF,DH,DS,?,?,?> fbDb;
	
	public JeeslIoDbFacadeBean(EntityManager em, final IoDbFactoryBuilder<L,D,SYSTEM,DUMP,DF,DH,DS,?,?,?> fbDb){this(em,fbDb,false);}
	public JeeslIoDbFacadeBean(EntityManager em, final IoDbFactoryBuilder<L,D,SYSTEM,DUMP,DF,DH,DS,?,?,?> fbDb, boolean handleTransaction)
	{
		super(em,handleTransaction);
		this.fbDb=fbDb;
	}
	
	@Override public List<DF> fDumpFiles(DH host) 
	{
		return this.allForParent(fbDb.getClassDumpFile(),JeeslDbDumpFile.Attributes.host.toString(), host);
	}
	
	@Override public DF fDumpFile(DUMP dump, DH host) throws UtilsNotFoundException
	{
		return this.oneForParents(fbDb.getClassDumpFile(), JeeslDbDumpFile.Attributes.dump.toString(), dump, JeeslDbDumpFile.Attributes.host.toString(), host);
	}
	
	@Override public String version()
	{
		Query q = em.createQuery("select version()");

		Object o = q.getSingleResult();
		return (String)o;
	}
	
	@Override public long countExact(Class<?> c)
	{
		Query q = em.createQuery("select count(*) FROM "+ c.getSimpleName());

		Object o = q.getSingleResult();
		return (Long)o;
	}
	
	@Override public long countEstimate(Class<?> c)
	{
		if(c.getAnnotation(javax.persistence.Table.class)!=null)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT CAST(reltuples AS BIGINT)");
			sb.append(" FROM pg_class");
			sb.append(" WHERE relname='").append(c.getAnnotation(javax.persistence.Table.class).name().toLowerCase()).append("';");
			
			Query q = em.createNativeQuery(sb.toString());
			for(Object o : q.getResultList())
	        {
				BigInteger i = (BigInteger)o;
	            return i.longValue();
	        }
		}
		else
		{
			return -1;
		}
		return -1;
	}
	
	@Override
	public Map<Class<?>, Long> count(List<Class<?>> list)
	{
		Map<Class<?>, Long> result = new Hashtable<Class<?>,Long>();
		for(Class<?> c : list)
		{
			result.put(c,countExact(c));
		}
		return result;
	}
	
	@Override
	public Table connections(String dbName)
	{
		List<String> fileds = new ArrayList<String>();
		fileds.add("pid");
//		fileds.add("datname");
//		fileds.add("client_addr");
		fileds.add("state");
		fileds.add("xact_start");
		fileds.add("query");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT "+StringUtil.join(fileds, ","));
		sb.append(" FROM pg_stat_activity");
		sb.append(" WHERE datname='"+dbName+"'");
		logger.info(sb.toString());
		
		
		List<Object[]> data = new ArrayList<Object[]>();
		for(Object o : em.createNativeQuery(sb.toString()).getResultList())
		{
			Object[] array = (Object[])o;
			data.add(array);
//			debugDataTypes(array);
		}
		
		Table table = XmlTableFactory.build(fileds,data);
		
		return table;
	}
	
	public List<JsonPostgresConnection> postgresConnections(String dbName)
	{
		List<String> fileds = new ArrayList<String>();
		fileds.add("pid");
		fileds.add("state");
		fileds.add("xact_start");
		fileds.add("query");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT "+StringUtil.join(fileds, ","));
		sb.append(" FROM pg_stat_activity");
		sb.append(" WHERE datname='"+dbName+"'");
		logger.info(sb.toString());
		
		
		List<JsonPostgresConnection> list = new ArrayList<>();
		for(Object o : em.createNativeQuery(sb.toString()).getResultList())
		{
			Object[] array = (Object[])o;
			
			
			debugDataTypes(array);
		}
	
		return list;
	}
	
	@Override
	public List<JsonPostgresReplication> postgresReplicationInfo()
	{
		List<String> fileds = new ArrayList<String>();
		fileds.add("*");
		fileds.add("state::char(12)");
		fileds.add("extract('milliseconds' from write_lag) as wl");
		fileds.add("extract('milliseconds' from flush_lag) as fl");
		fileds.add("extract('milliseconds' from replay_lag) as rl");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT "+StringUtil.join(fileds, ", "));
		sb.append(" FROM pg_stat_replication");
		logger.info(sb.toString());
		
		
		List<JsonPostgresReplication> list = new ArrayList<>();
		List<Object[]> data = new ArrayList<Object[]>();
		for(Object o : em.createNativeQuery(sb.toString()).getResultList())
		{
			Object[] array = (Object[])o;
			data.add(array);
			debugDataTypes(array);
			
			JsonPostgresReplication r = new JsonPostgresReplication();
			r.setId((Integer)array[0]);
			r.setState(array[1].toString());
//			r.setClient_addr(array[2].toString());
//			r.setSync_state(array[3].toString());
			
//			if (array[2]!=null) {r.setWrite_lag((Long)array[2]);} else {r.setWrite_lag(0);}
//			if (array[3]!=null) {r.setFlush_lag((Long)array[3]);} else {r.setFlush_lag(0);}
//			if (array[4]!=null) {r.setReplay_lag((Long)array[4]);} else {r.setReplay_lag(0);}
			list.add(r);
		}
		
		return list;
	}
	
	@Override public JsonFlatFigures dbConnections(String dbName)
	{		
		JsonFlatFigures flats = JsonFlatFiguresFactory.build();
		
		int i=1;
		for(Object o : em.createNativeQuery(SqlDbPgStatFactory.connections(dbName)).getResultList())
		{
			Object[] array = (Object[])o;
			flats.getFigures().add(JsonDbPgStatConnectionFactory.build(i,array));
			i++;
		}		
		return flats;
	}
	
	@Override public JsonFlatFigures dbQueries(String dbName)
	{		
		JsonFlatFigures flats = JsonFlatFiguresFactory.build();
		
		int i=1;
		for(Object o : em.createNativeQuery(SqlDbPgStatFactory.queries(dbName)).getResultList())
		{
			Object[] array = (Object[])o;
			flats.getFigures().add(JsonDbPgStatQueryFactory.build(i,array));
			i++;
		}		
		return flats;
	}
	
	public static void debugDataTypes(Object[] array)
	{
		logger.info("*****************************");
		int i=0;
		for(Object o : array)
		{
			if(o!=null)
			{
				logger.info(i+" "+o.getClass().getName());
			}
			else
			{
				logger.info(i+" NULL");
			}
			i++;
		}
	}
}