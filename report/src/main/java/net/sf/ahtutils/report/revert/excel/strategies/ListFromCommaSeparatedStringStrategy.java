package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListFromCommaSeparatedStringStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(ListFromCommaSeparatedStringStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		String value = object.toString();
		
		List<String> list = Arrays.asList(value.split("\\s*,\\s*"));
		
    	return list;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
