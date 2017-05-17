package org.jeesl.factory.xml.module.inventory;

import org.jeesl.model.xml.module.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlInventoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlInventoryFactory.class);
	
	public static Inventory build()
	{
		Inventory xml = new Inventory();

		return xml;
	}
}