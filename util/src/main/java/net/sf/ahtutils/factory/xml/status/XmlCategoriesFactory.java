package net.sf.ahtutils.factory.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Categories;

public class XmlCategoriesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCategoriesFactory.class);
	
	public static Categories build()
	{
		Categories xml = new Categories();
		
		return xml;
	}
}