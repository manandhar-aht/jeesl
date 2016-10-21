package org.jeesl.model.xml.navigation;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.navigation.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMenu extends AbstractXmlNavigationTest<Menu>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMenu.class);
	
	public TestXmlMenu(){super(Menu.class);}
	public static Menu create(boolean withChildren){return (new TestXmlMenu()).build(withChildren);} 
    
    public Menu build(boolean withChilds)
    {
    	Menu xml = new Menu();
      	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlMenu test = new TestXmlMenu();
		test.saveReferenceXml();
    }
}