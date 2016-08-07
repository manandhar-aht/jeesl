package net.sf.ahtutils.xml.navigation;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.access.TestXmlView;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlMenuItem extends AbstractXmlNavigationTest<MenuItem>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMenuItem.class);
	
	public TestXmlMenuItem(){super(MenuItem.class);}
	public static MenuItem create(boolean withChildren){return (new TestXmlMenuItem()).build(withChildren);}
    
    public MenuItem build(boolean withChilds)
    {
    	MenuItem xml = new MenuItem();
      	xml.setActive(true);
      	xml.setCode("myCode");
      	xml.setName("myName");
      	xml.setHref("myHref");
    	
    	if(withChilds)
    	{
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setView(TestXmlView.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlMenuItem test = new TestXmlMenuItem();
		test.saveReferenceXml();
    }
}