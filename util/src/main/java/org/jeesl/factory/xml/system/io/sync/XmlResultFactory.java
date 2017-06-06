package org.jeesl.factory.xml.system.io.sync;

import net.sf.ahtutils.xml.sync.Result;

import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlResultFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlResultFactory.class);
		
	public static Result buildOk(){return build("success");}
	public static Result buildFail() {return build("fail");}
	
	public static Result build(String statusCode)
	{
		Result xml = new Result();
		xml.setStatus(XmlStatusFactory.create(statusCode));
		return xml;
	}
}