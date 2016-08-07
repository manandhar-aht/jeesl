package net.sf.ahtutils.xml.access;

import java.io.File;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlAccessTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlAccessTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/access";
	protected static File fXml;
}