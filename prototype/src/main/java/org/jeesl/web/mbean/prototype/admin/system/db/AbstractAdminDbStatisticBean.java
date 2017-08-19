package org.jeesl.web.mbean.prototype.admin.system.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractAdminDbStatisticBean <L extends UtilsLang, D extends UtilsDescription,
											DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
											FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
											HOST extends UtilsStatus<HOST,L,D>,
											STATUS extends UtilsStatus<STATUS,L,D>> 
implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbStatisticBean.class);
	
	protected JeeslIoDbFacade<L,D,DUMP,FILE,HOST,STATUS> fDb;
	
	protected List<Class<?>> list = new ArrayList<Class<?>>();
	public List<Class<?>> getList(){return list;}
	
	private Map<Class<?>,Long> map;
	public Map<Class<?>, Long> getMap(){return map;}

	public AbstractAdminDbStatisticBean()
	{
		list = new ArrayList<Class<?>>();
	}
	
	public void refresh()
	{
		map = fDb.count(list);
		for(Class<?> cl : list)
		{
			logger.info(cl.getSimpleName()+": "+map.get(cl));
		}
	}
}