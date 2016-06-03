package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;
import net.sf.ahtutils.xml.status.Description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetDescriptionStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(SetDescriptionStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		
		String code = object.toString();
		
		Class  lutClass			 = null;
    	Description description  = new Description();
		
		description.setValue(code);
    	
		return description;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
