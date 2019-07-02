package org.jeesl.factory.xml.module.ts;

import org.jeesl.model.xml.module.ts.Entity;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlEntityFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEntityFactory.class);
	
	public static Entity build(String code)
	{
		Entity xml = new Entity();
		if(code!=null){xml.setCode(code);}
		return xml;
	}
}