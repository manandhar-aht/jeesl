package org.jeesl.model.xml.navigation;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.navigation.Navigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlNavigation extends AbstractXmlNavigationTest<Navigation>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlNavigation.class);

	public TestXmlNavigation(){super(Navigation.class);}
	public static Navigation create(boolean withChildren){return (new TestXmlNavigation()).build(withChildren);}
    
    public Navigation build(boolean withChilds)
    {
    	Navigation xml = new Navigation();
    	xml.setCode("myCode");
    	xml.setPackage("myPackage");
    	
    	if(withChilds)
    	{
    		xml.setViewPattern(TestXmlViewPattern.create(false));
        	xml.setUrlMapping(TestXmlUrlMapping.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlNavigation test = new TestXmlNavigation();
		test.saveReferenceXml();
    }
}