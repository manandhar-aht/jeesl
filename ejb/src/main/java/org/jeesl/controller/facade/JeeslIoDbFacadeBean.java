
package org.jeesl.controller.facade;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jeesl.factory.json.system.db.JsonDbConnectionFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFiguresFactory;
import org.jeesl.factory.sql.system.db.SqlDbConnectionsFactory;
import org.jeesl.interfaces.facade.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.model.json.JsonFlatFigures;
import org.jsoup.helper.StringUtil;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoDbFacadeBean <L extends UtilsLang,D extends UtilsDescription,
								DUMP extends JeeslDbDump<L,D,DUMP,STORE,HOST,STATUS>,
								STORE extends JeeslDbDumpFile<L,D,DUMP,STORE,HOST,STATUS>,
								HOST extends UtilsStatus<HOST,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>>
		extends UtilsFacadeBean implements JeeslIoDbFacade
{
	final static Logger logger = LoggerFactory.getLogger(JeeslIoDbFacadeBean.class);
	
	public JeeslIoDbFacadeBean(EntityManager em){this(em,false);}
	public JeeslIoDbFacadeBean(EntityManager em, boolean handleTransaction)
	{
		super(em,handleTransaction);
	}
	
	@Override public String version()
	{
		Query q = em.createQuery("select version()");

		Object o = q.getSingleResult();
		return (String)o;
	}
	
	@Override public  long count(Class<?> cl)
	{
		Query q = em.createQuery("select count(*) FROM "+ cl.getSimpleName());

		Object o = q.getSingleResult();
		return (Long)o;
	}
	
	@Override
	public Map<Class<?>, Long> count(List<Class<?>> list)
	{
		Map<Class<?>, Long> result = new Hashtable<Class<?>,Long>();
		for(Class<?> c : list)
		{
			result.put(c,count(c));
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
		
		List<Object[]> data = new ArrayList<Object[]>();
		for(Object o : em.createNativeQuery("SELECT "+StringUtil.join(fileds, ",")+" FROM pg_stat_activity WHERE datname='"+dbName+"'").getResultList())
		{
			Object[] array = (Object[])o;
			data.add(array);
//			debugDataTypes(array);
		}
		
		Table table = OfxTableFactory.build(fileds,data);
		
		return table;
	}
	
	@Override public JsonFlatFigures dbConnections(String dbName)
	{		
		JsonFlatFigures flats = JsonFlatFiguresFactory.build();
		
		for(Object o : em.createNativeQuery(SqlDbConnectionsFactory.build(dbName)).getResultList())
		{
			Object[] array = (Object[])o;
			flats.getFigures().add(JsonDbConnectionFactory.build(array));
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