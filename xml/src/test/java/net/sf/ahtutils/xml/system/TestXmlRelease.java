package net.sf.ahtutils.xml.system;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;

public class TestXmlRelease extends AbstractXmlSystemTest<Release>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRelease.class);
	
	public TestXmlRelease(){super(Release.class);}
	public static Release create(boolean withChildren){return (new TestXmlRelease()).build(withChildren);}
        
    public Release build(boolean withChilds)
    {
    	Release xml = new Release();
    	xml.setVersion("0.1.1");
    	xml.setDate(AbstractXmlSystemTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlRelease test = new TestXmlRelease();
		test.saveReferenceXml();
    }
}