package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

import org.jeesl.api.controller.ImportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByNumericCodeStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByNumericCodeStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
            Double d = (Double) object;
            String code = d.intValue() +"";
            logger.debug(".. searching for Entity with numeric code of " +code);
            
		
		Class  lutClass      = null;
    	Object lookupEntity  = null;
    	
		if (code == null && !tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			return null;
		}
		else if (code == null && tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			code = "unknown";
		}
		logger.debug("Searching for Entity with Code " +code);
    	try {
    		lutClass = (Class) Class.forName(parameterClass);
	    	lookupEntity = facade.fByCode(lutClass, code);
                logger.debug("Found entity " +lookupEntity.toString());
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
