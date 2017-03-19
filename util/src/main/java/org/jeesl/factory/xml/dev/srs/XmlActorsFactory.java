package org.jeesl.factory.xml.dev.srs;

import org.jeesl.model.xml.dev.srs.Actors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlActorsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlActorsFactory.class);
		
    public static Actors build()
    {
    	Actors xml = new Actors();

    	return xml;
    }
}