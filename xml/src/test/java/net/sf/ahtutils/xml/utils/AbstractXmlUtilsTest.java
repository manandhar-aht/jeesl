package net.sf.ahtutils.xml.utils;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlUtilsTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlUtilsTest.class);
	
    protected static final String dirSuffix = "utils";
	protected static final String rootDir = "src/test/resources/data/xml/status";
}