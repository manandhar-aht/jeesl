package net.sf.ahtutils.xml.security;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.system.AbstractXmlSystemTest;

public abstract class AbstractXmlSecurityTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlSecurityTest(Class<T> cXml)
	{
		super(cXml,"security");
	}
}