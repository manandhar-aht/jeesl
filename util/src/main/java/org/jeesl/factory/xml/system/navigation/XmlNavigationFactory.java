package org.jeesl.factory.xml.system.navigation;

import org.jeesl.model.xml.system.navigation.Navigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlNavigationFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlNavigationFactory.class);
		
	@SuppressWarnings("unused")
	private Navigation q;
	
	public XmlNavigationFactory(Navigation q)
	{
		this.q=q;
	}
}