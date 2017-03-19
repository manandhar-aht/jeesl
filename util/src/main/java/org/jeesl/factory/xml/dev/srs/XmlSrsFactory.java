package org.jeesl.factory.xml.dev.srs;

import org.jeesl.model.xml.dev.srs.Srs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSrsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSrsFactory.class);
		
    public static Srs build()
    {
    	Srs xml = new Srs();
    	return xml;
    }
}