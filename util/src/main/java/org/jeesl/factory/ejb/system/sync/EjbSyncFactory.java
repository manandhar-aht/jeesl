package org.jeesl.factory.ejb.system.sync;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.sync.UtilsSync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSyncFactory<L extends UtilsLang,
							D extends UtilsDescription,
							STATUS extends UtilsStatus<STATUS,L,D>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							SYNC extends UtilsSync<L,D,STATUS,CATEGORY>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSyncFactory.class);
	
	final Class<SYNC> cSync;
    
	public EjbSyncFactory(final Class<SYNC> cSync)
	{       
        this.cSync = cSync;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,STATUS extends UtilsStatus<STATUS,L,D>,CATEGORY extends UtilsStatus<CATEGORY,L,D>,SYNC extends UtilsSync<L,D,STATUS,CATEGORY>>  EjbSyncFactory<L,D,STATUS,CATEGORY,SYNC>
		factory(final Class<SYNC> cSync)
	{
		return new EjbSyncFactory<L,D,STATUS,CATEGORY,SYNC>(cSync);
	}
    
	public SYNC build(CATEGORY category, STATUS status,String code){return build(category,status,code,new Date());}
	public SYNC build(CATEGORY category, STATUS status,String code,Date record)
	{
		SYNC ejb = null;
		try
		{
			ejb = cSync.newInstance();
			ejb.setCategory(category);
			ejb.setStatus(status);
			ejb.setCode(code);
			ejb.setRecord(record);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}