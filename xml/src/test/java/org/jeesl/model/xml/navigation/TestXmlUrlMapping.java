package org.jeesl.model.xml.navigation;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.navigation.UrlMapping;

public class TestXmlUrlMapping extends AbstractXmlNavigationTest<UrlMapping>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUrlMapping.class);
	
	public TestXmlUrlMapping(){super(UrlMapping.class);}
	public static UrlMapping create(boolean withChildren){return (new TestXmlUrlMapping()).build(withChildren);}
    
    public UrlMapping build(boolean withChilds)
    {
    	UrlMapping xml = new UrlMapping();
    	xml.setValue("myUrlMapping");
    	xml.setUrl("myUrl");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlUrlMapping test = new TestXmlUrlMapping();
		test.saveReferenceXml();
    }
}