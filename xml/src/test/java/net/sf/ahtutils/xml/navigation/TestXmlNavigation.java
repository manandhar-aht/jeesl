package net.sf.ahtutils.xml.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

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
		UtilsXmlTestBootstrap.init();
		TestXmlNavigation test = new TestXmlNavigation();
		test.saveReferenceXml();
    }
}