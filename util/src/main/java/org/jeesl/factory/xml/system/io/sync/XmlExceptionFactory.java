package org.jeesl.factory.xml.system.io.sync;


import java.util.Date;

import net.sf.exlp.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlExceptionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlExceptionFactory.class);
			
	public static net.sf.ahtutils.xml.sync.Exception build(Throwable e)
	{
		net.sf.ahtutils.xml.sync.Exception xml = new net.sf.ahtutils.xml.sync.Exception();
		xml.setType(e.getClass().getName());
		xml.setMessage(e.getMessage());
		xml.setRecord(DateUtil.toXmlGc(new Date()));
		
		if(e.getStackTrace() != null && e.getStackTrace().length>0)
		{
			StackTraceElement st = e.getStackTrace()[0];
			xml.setClassName(st.getClassName());
			xml.setLine(st.getLineNumber());
		}
		
		if(e.getCause() != null)
		{
			xml.setException(XmlExceptionFactory.build(e.getCause()));
		}
		
		if(xml.isSetException()){removeRecursiveDates(xml.getException());}
		
		return xml;
	}
	
	private static void removeRecursiveDates(net.sf.ahtutils.xml.sync.Exception xml)
	{
		xml.setRecord(null);
		if(xml.isSetException()){removeRecursiveDates(xml.getException());}
	}
}