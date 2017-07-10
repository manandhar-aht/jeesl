package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

import org.jeesl.api.controller.ImportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByIdStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByIdStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		Long id              = (Long) object;
		Class  lutClass      = null;
    	Object lookupEntity  = null;
    	
		if (id == null && !tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			return null;
		}
		logger.debug("Searching for Entity with ID " +id);
    	try {
    		lutClass = (Class) Class.forName(parameterClass);
	    	lookupEntity = facade.find(lutClass, id);
		} catch (Exception e) {
			logger.error("An error occured while trying to import! " +e.getMessage());
		}
    	return lookupEntity;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
