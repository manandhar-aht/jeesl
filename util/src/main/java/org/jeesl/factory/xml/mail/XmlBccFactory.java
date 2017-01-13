package org.jeesl.factory.xml.mail;

import org.jeesl.model.xml.system.io.mail.Bcc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBccFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEmailAddressFactory.class);
    
    public static Bcc build()
    {    	
    	return new Bcc();
    }
}