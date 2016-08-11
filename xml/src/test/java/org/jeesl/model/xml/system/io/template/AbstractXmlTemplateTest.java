package org.jeesl.model.xml.system.io.template;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlTemplateTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTemplateTest.class);
	
	public AbstractXmlTemplateTest(Class<T> cXml)
	{
		super(cXml,"system/io/template");
	}
}