package org.jeesl.model.xml.module.inventory.pc;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlInventoryPcTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlInventoryPcTest.class);

	public AbstractXmlInventoryPcTest(Class<T> cXml)
	{
		super(cXml,"module/inventory/pc");
	}
}