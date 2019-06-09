package org.jeesl.web.mbean.prototype.admin.system.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbHost;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractAdminDbStatisticBean <L extends UtilsLang, D extends UtilsDescription,
											SYSTEM extends JeeslIoSsiSystem,
											DUMP extends JeeslDbDump<SYSTEM,FILE>,
											FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
											HOST extends JeeslDbHost<HOST,L,D,?>,
											STATUS extends JeeslDbDumpStatus<STATUS,L,D,?>> 
implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbStatisticBean.class);
	
	protected JeeslIoDbFacade<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fDb;
	
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