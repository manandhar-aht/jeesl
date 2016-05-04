package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;


import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;
import net.sf.ahtutils.xml.text.Remark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddRemarkStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(AddRemarkStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		String value = object.toString();
		
		Remark remark = new Remark();
		remark.setValue(value);
		
    	return remark;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
