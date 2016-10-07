package org.jeesl.factory.xml.system.navigation;

import net.sf.ahtutils.xml.access.View;

import org.jeesl.model.xml.system.navigation.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewPatternFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewPatternFactory.class);
		
	public static MenuItem clone(MenuItem item)
	{
		MenuItem xml = new MenuItem();
		xml.setName(item.getName());
		xml.setHref(item.getHref());
		xml.setCode(item.getCode());

		return xml;
	}
	
	public static MenuItem dynamic(String dynamicCode, String urlParameter, String label)
	{
		View view = new View();
		view.setCode(dynamicCode);
		view.setUrlParameter(urlParameter);
		view.setLabel(label);

		MenuItem item = new MenuItem();
		item.setCode(dynamicCode+view.getUrlParameter());
		item.setView(view);
		return item;
	}
	
	
}