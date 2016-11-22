package org.jeesl.factory.xml.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Bcc;

public class XmlBccFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEmailAddressFactory.class);
    
    public static Bcc build()
    {    	
    	return new Bcc();
    }
}