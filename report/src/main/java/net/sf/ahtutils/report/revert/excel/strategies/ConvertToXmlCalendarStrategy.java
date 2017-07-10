package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

import org.jeesl.api.controller.ImportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertToXmlCalendarStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(ConvertToXmlCalendarStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		GregorianCalendar c           = new GregorianCalendar();
		XMLGregorianCalendar calendar = null;
		
		c.setTime((Date) object);
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			logger.warn("Calendar creation/conversion failed! Reason: " +e.getMessage());
		}
    	return calendar;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
