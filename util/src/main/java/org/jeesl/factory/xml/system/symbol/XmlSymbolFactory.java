package org.jeesl.factory.xml.system.symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Symbol;

public class XmlSymbolFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSymbolFactory.class);
			
	public static Symbol build()
	{
		Symbol xml = new Symbol();
		return xml;
	}
}