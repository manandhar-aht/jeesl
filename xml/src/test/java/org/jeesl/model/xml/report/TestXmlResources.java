package org.jeesl.model.xml.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Resources;

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
		JeeslXmlTestBootstrap.init();
		TestXmlResources test = new TestXmlResources();
		test.saveReferenceXml();
    }
}