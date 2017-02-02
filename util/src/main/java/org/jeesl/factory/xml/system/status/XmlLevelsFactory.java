package org.jeesl.factory.xml.system.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Level;
import net.sf.ahtutils.xml.status.Levels;

public class XmlLevelsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLevelsFactory.class);
		
	public static Levels build()
	{
		return new Levels();
	}
	
	public static Levels build(Level level)
	{
		Levels xml = build();
		xml.getLevel().add(level);
		return xml;
	}
}