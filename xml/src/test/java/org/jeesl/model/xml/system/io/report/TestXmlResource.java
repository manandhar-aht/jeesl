package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Resource;
import net.sf.ahtutils.xml.report.Resource.Value;

public class TestXmlResource extends AbstractXmlReportTest<Resource>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResource.class);
	
	public TestXmlResource(){super(Resource.class);}
	public static Resource create(boolean withChildren){return (new TestXmlResource()).build(withChildren);}
    
    public Resource build(boolean withChildren)
    {
    	Resource resource = new Resource();
    	resource.setName("logo");
    	resource.setType("image");
    	Value myValue = new Resource.Value();
    	myValue.setValue("logo.png");
    	resource.setValue(myValue);
    	return resource;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlResource test = new TestXmlResource();
		test.saveReferenceXml();
    }
}