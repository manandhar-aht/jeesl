package org.jeesl.model.xml.navigation;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.system.navigation.ViewPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViewPattern extends AbstractXmlNavigationTest<ViewPattern>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViewPattern.class);
	
	public TestXmlViewPattern(){super(ViewPattern.class);}
	public static ViewPattern create(boolean withChildren){return (new TestXmlViewPattern()).build(withChildren);}
    
    public ViewPattern build(boolean withChilds)
    {
    	ViewPattern xml = new ViewPattern();
    	xml.setValue("myViewPattern");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlViewPattern test = new TestXmlViewPattern();
		test.saveReferenceXml();
    }
}