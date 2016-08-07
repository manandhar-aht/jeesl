package net.sf.ahtutils.xml.report;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlResources extends AbstractXmlReportTest<Resources>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResources.class);
	
	public TestXmlResources(){super(Resources.class);}
	public static Resources create(boolean withChildren){return (new TestXmlResources()).build(withChildren);} 
    
    public Resources build(boolean withChildren)
    {
    	Resources resources = new Resources();
    	resources.setDir("src/main/resources/report/resources");
    	resources.getResource().add(TestXmlResource.create(false));
    	return resources;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlResources test = new TestXmlResources();
		test.saveReferenceXml();
    }
}