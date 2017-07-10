package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.db.xml.UtilsIdMapper;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.util.reflection.ReflectionsUtil;

import org.jeesl.api.controller.ImportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByMappedIdStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByMappedIdStrategy.class);
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		Number number        = (Number) object;
		Long id              = new Long(number.longValue());
		Class<?>  lutClass   = null;
    	Object lookupEntity  = null;
    	
    	try {
    		lutClass = (Class<?>) Class.forName(parameterClass);
	    	
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		UtilsIdMapper mapper = (UtilsIdMapper) this.tempPropertyStore.get("idMapper");
		if (mapper.isObjectMapped(lutClass, id))
		{
			lookupEntity = mapper.getMappedObject(lutClass, id);
		}
		else
		{
			try {
				lookupEntity = lutClass.newInstance();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				ReflectionsUtil.simpleInvokeMethod("setId",
					      new Object[] { id.longValue() },
					      lutClass,
					      lookupEntity);
			} catch (Exception e) {
				logger.error("Could not set ID for created " +lutClass.getSimpleName());
				logger.error(e.getMessage());
			}
			mapper.addObjectForId(id, lookupEntity);
		}
    	return lookupEntity;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		logger.trace("The strategy " +this.getClass().getSimpleName() +" is not depending on database operations - no Facade needed!");
	}
}
