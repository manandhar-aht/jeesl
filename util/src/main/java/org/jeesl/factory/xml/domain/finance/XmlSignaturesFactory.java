package org.jeesl.factory.xml.domain.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Signatures;

public class XmlSignaturesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSignaturesFactory.class);
	
    public static Signatures build()
    {
    	Signatures xml = new Signatures();
    	return xml;
    }
}