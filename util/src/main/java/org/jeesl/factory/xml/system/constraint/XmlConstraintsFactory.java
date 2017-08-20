package org.jeesl.factory.xml.system.constraint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.system.Constraints;

public class XmlConstraintsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlConstraintsFactory.class);
	
	public static Constraints build()
	{
		Constraints xml = new Constraints();
		return xml;
	}
}