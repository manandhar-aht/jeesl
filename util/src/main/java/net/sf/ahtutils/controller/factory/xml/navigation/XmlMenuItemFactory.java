package net.sf.ahtutils.controller.factory.xml.navigation;

import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.status.Lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMenuItemFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMenuItemFactory.class);
		
	private Lang q;
	
	public XmlMenuItemFactory(Lang q)
	{
		this.q=q;
	}
	
	public static MenuItem create(String label)
	{
		MenuItem xml = new MenuItem();
		xml.setName(label);
		return xml;
	}
	
	public static MenuItem build(MenuItem mi)
	{
		MenuItem xml = new MenuItem();
		xml.setActive(mi.isSetActive() && mi.isActive());
		xml.setCode(mi.getCode());
		xml.setHref(mi.getHref());
		xml.setName(mi.getName());
		
		return xml;
	}
}