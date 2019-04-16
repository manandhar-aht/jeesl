package org.jeesl.factory.xml.module.ts;

import org.jeesl.model.xml.module.ts.Ts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTsFactory.class);
	
	public static Ts build()
	{
		return new Ts();
	}
}