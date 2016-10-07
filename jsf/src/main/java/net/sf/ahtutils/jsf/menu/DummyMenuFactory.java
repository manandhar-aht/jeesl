package net.sf.ahtutils.jsf.menu;

import org.jeesl.model.xml.system.navigation.Menu;
import org.jeesl.model.xml.system.navigation.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyMenuFactory
{
	final static Logger logger = LoggerFactory.getLogger(DummyMenuFactory.class);
	
	public static Menu create()
	{	
		Menu menu = new Menu();
		menu.getMenuItem().add(createTop1());
		menu.getMenuItem().add(createTop2());
		menu.getMenuItem().add(createTop3());
		menu.getMenuItem().add(createTop4());
		
		return menu;
	}
	
	private static MenuItem createTop1()
	{
		MenuItem top1 = createItem("Top-1","hr-t1",true);
		
		MenuItem sublevel1 = createItem("Sub-1.1","sub-1.1",true);
		sublevel1.getMenuItem().add(createItem("Sub-1.1.1","sub-1.1.1",true));
		sublevel1.getMenuItem().add(createItem("Sub-1.1.2","sub-1.1.1",false));
		
		top1.getMenuItem().add(sublevel1);
		
		return top1;
	}
	
	private static MenuItem createTop2()
	{
		MenuItem top2 = createItem("Top-2","hr-t2",false);
		
		MenuItem sublevel1 = createItem("Sub-2.1","sub-2.1",false);
		sublevel1.getMenuItem().add(createItem("Sub-2.1.1","sub-2.1.1",false));
		sublevel1.getMenuItem().add(createItem("Sub-2.1.2","sub-2.1.1",false));
		
		MenuItem sublevel2 = createItem("Sub-2.2","sub-2.2",false);
		sublevel2.getMenuItem().add(createItem("Sub-2.2.1","sub-2.2.1",false));
		sublevel2.getMenuItem().add(createItem("Sub-2.2.2","sub-2.2.1",false));
		
		top2.getMenuItem().add(sublevel1);
		top2.getMenuItem().add(sublevel2);
		
		return top2;
	}
	
	private static MenuItem createTop3()
	{
		MenuItem top3 = createItem("Top-3","hr-t3",false);
		
		MenuItem sublevel1 = createItem("Sub-3.1","sub-3.1",false);
		sublevel1.getMenuItem().add(createItem("Sub-3.1.1","sub-3.1.1",false));
		sublevel1.getMenuItem().add(createItem("Sub-3.1.2","sub-3.1.1",false));
		
		MenuItem sublevel2 = createItem("Sub-3.2","sub-3.2",false);
		sublevel2.getMenuItem().add(createItem("Sub-3.2.1","sub-3.2.1",false));
		sublevel2.getMenuItem().add(createItem("Sub-3.2.2","sub-3.2.1",false));
		
		MenuItem sublevel3 = createItem("Sub-3.3","sub-3.3",false);
		sublevel3.getMenuItem().add(createItem("Sub-3.3.1","sub-3.3.1",false));
		sublevel3.getMenuItem().add(createItem("Sub-3.3.2","sub-3.3.1",false));
		
		top3.getMenuItem().add(sublevel1);
		top3.getMenuItem().add(sublevel2);
		top3.getMenuItem().add(sublevel3);
		
		return top3;
	}
	
	private static MenuItem createTop4()
	{
		MenuItem top4 = createItem("Top-4","hr-t4",false);
		
		MenuItem sublevel1 = createItem("Sub-4.1","sub-4.1",false);
		sublevel1.getMenuItem().add(createItem("Sub-4.1.1","sub-4.1.1",false));
		sublevel1.getMenuItem().add(createItem("Sub-4.1.2","sub-4.1.1",false));
		
		MenuItem sublevel2 = createItem("Sub-4.2","sub-4.2",false);
		sublevel2.getMenuItem().add(createItem("Sub-4.2.1","sub-4.2.1",false));
		sublevel2.getMenuItem().add(createItem("Sub-3.2.2","sub-4.2.1",false));
		
		MenuItem sublevel3 = createItem("Sub-4.3","sub-4.3",false);
		sublevel3.getMenuItem().add(createItem("Sub-4.3.1","sub-4.3.1",false));
		sublevel3.getMenuItem().add(createItem("Sub-4.3.2","sub-4.3.1",false));
		
		MenuItem sublevel4 = createItem("Sub-4.4","sub-4.4",false);
		sublevel4.getMenuItem().add(createItem("Sub-4.4.1","sub-4.4.1",false));
		sublevel4.getMenuItem().add(createItem("Sub-4.4.2","sub-4.4.1",false));
		
		top4.getMenuItem().add(sublevel1);
		top4.getMenuItem().add(sublevel2);
		top4.getMenuItem().add(sublevel3);
		top4.getMenuItem().add(sublevel4);
		
		return top4;
	}
	
	private static MenuItem createItem(String name, String href, boolean active)
	{
		MenuItem item = new MenuItem();
		item.setActive(active);
		item.setName(name);
		item.setHref(href);
		return item;
	}
}
