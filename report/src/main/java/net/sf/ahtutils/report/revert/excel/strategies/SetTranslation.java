package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

import org.jeesl.api.controller.ImportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetTranslation implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(SetTranslation.class);
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		
		// Build a map to contain the translation in the requested locale, stored in a Lang or Description object's lang property
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("Setting " +object.toString() +" as translated " +property);
		try {
			String	languageKey			= (String) this.tempPropertyStore.get("languageKey");
		
			UtilsLang description = (UtilsLang) this.tempPropertyStore.get("UtilsDescriptionImpl").getClass().newInstance();
			description.setLang(object.toString());
			description.setLkey(languageKey);
			map.put(languageKey, description);
			logger.info("Created Map for " +languageKey +" translation containing " +description.getLang());
			logger.info("Container is of class " +description.getClass().getCanonicalName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		logger.trace("The strategy " +this.getClass().getSimpleName() +" is not depending on database operations - no Facade needed!");
	}
}
