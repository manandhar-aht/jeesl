package org.jeesl.model.xml.module.inventory;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlInventoryTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlInventoryTest.class);

	public AbstractXmlInventoryTest(Class<T> cXml)
	{
		super(cXml,"module/inventory");
	}
}