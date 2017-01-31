package org.jeesl.factory.xml.domain.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Signature;

public class XmlSignatureFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSignatureFactory.class);
	
    public static Signature build()
    {
    	Signature xml = new Signature();
    	return xml;
    }
}