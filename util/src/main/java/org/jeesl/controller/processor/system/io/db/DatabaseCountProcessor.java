package org.jeesl.controller.processor.system.io.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.processor.module.ts.AbstractTimeSeriesProcessor;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class DatabaseCountProcessor<RE extends JeeslRevisionEntity<?,?,?,?,?>,
									SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
									TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
									TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
									BRIDGE extends JeeslTsBridge<EC>,
									EC extends JeeslTsEntityClass<?,?,?>,
									INT extends UtilsStatus<INT,?,?>,
									DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
									WS extends UtilsStatus<WS,?,?>>
	extends AbstractTimeSeriesProcessor<SCOPE,TS,TRANSACTION,BRIDGE,EC,INT,DATA,WS>
{
	final static Logger logger = LoggerFactory.getLogger(DatabaseCountProcessor.class);
	
	private final IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?> fbRevision;
		
	private final JeeslIoDbFacade<?,?,?,?,?,?,?> fDb;
	
	public DatabaseCountProcessor(IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?> fbRevision,
									TsFactoryBuilder<?,?,?,SCOPE,?,?,?,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,?,?,?,WS,?> fbTs,
									JeeslIoDbFacade<?,?,?,?,?,?,?> fDb,
									JeeslTsFacade<?,?,?,SCOPE,?,?,?,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,?,?,?,WS,?> fTs)
	{
		super(fbTs,fTs);
		this.fbRevision=fbRevision;
		this.fDb=fDb;
	}
	
	public void count() throws IllegalStateException
	{
		if(!isInitialized()) {throw new IllegalStateException(this.getClass().getSimpleName()+" is not fully initialized");}
		List<RE> listAll = fDb.all(fbRevision.getClassEntity());
		List<RE> listTs = new ArrayList<RE>();
		
		for(RE entity : listAll)
		{
			try
			{
				Class<?> c = Class.forName(entity.getCode());
				
				boolean active = BooleanComparator.active(entity.getTimeseries());
				boolean table = c.getAnnotation(Table.class)!=null;
				
				if(active && table){listTs.add(entity);}
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
		}
		logger.info("Creating TS for "+listTs.size()+" of "+listAll.size());
		
		if(!listTs.isEmpty())
		{
			try
			{
				TRANSACTION transaction = fbTs.transaction().build(null,null);
				transaction = fTs.save(transaction);
				Date date = (new DateTime(new Date())).withTimeAtStartOfDay().toDate();
				for(RE entity : listTs)
				{
					try
					{
						Class<?> c = Class.forName(entity.getCode());
						
						count(transaction,date,entity,c);
					}
					catch (ClassNotFoundException e) {e.printStackTrace();}
					catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
				}
			}
			catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
		}
	}
	
	private void count(TRANSACTION transaction, Date date, RE entity, Class<?> c) throws UtilsConstraintViolationException, UtilsLockingException
	{
		BRIDGE bridge = fTs.fcBridge(fbTs.getClassBridge(),ec,entity);
		TS ts = fTs.fcTimeSeries(scope,interval,bridge);
		Long count = fDb.countEstimate(c);
		DATA data = efData.build(ws, ts, transaction, date, count.doubleValue());
		data = fTs.save(data);
		logger.info(entity.getCode()+" "+c.getAnnotation(Table.class).name()+" "+count);
	}
}