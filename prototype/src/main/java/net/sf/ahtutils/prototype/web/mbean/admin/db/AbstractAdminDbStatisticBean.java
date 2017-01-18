package net.sf.ahtutils.prototype.web.mbean.admin.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.facade.UtilsDbFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminDbStatisticBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbStatisticBean.class);
	
	protected UtilsDbFacade fDb;
	
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