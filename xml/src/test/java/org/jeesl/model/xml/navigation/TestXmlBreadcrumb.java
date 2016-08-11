package org.jeesl.model.xml.navigation;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.navigation.Breadcrumb;

public class TestXmlBreadcrumb extends AbstractXmlNavigationTest<Breadcrumb>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBreadcrumb.class);
	
	public TestXmlBreadcrumb(){super(Breadcrumb.class);}
	public static Breadcrumb create(boolean withChildren){return (new TestXmlBreadcrumb()).build(withChildren);}
    
    public Breadcrumb build(boolean withChilds)
    {
    	Breadcrumb xml = new Breadcrumb();
    	
    	if(withChilds)
    	{
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlBreadcrumb test = new TestXmlBreadcrumb();
		test.saveReferenceXml();
    }
}