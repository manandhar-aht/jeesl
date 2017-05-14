package org.jeesl.factory.xml.system.revision;

import org.jeesl.model.xml.system.revision.Revisions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRevisionsFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlRevisionsFactory.class);
	
	public static Revisions build()
	{
		return new Revisions();
	}
}