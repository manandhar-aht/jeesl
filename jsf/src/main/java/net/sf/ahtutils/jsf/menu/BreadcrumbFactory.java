package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.factory.xml.system.navigation.XmlMenuItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.navigation.Breadcrumb;
import net.sf.ahtutils.xml.navigation.MenuItem;

public class BreadcrumbFactory
{
	final static Logger logger = LoggerFactory.getLogger(BreadcrumbFactory.class);
	
	private List<MenuItem> list;
	
	public BreadcrumbFactory()
	{
		list = new ArrayList<MenuItem>();
	}
	
	public void add(String label,String href)
	{
		add(XmlMenuItemFactory.buildItem(label, href));
	}
	
	public void add(MenuItem item)
	{
		list.add(item);
	}
	
	public Breadcrumb build()
	{
		Breadcrumb breadcrumb = new Breadcrumb();
		breadcrumb.getMenuItem().addAll(list);
		return breadcrumb;
	}
}
