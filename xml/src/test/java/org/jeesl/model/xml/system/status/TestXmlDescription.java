package org.jeesl.model.xml.system.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Description;

public class TestXmlDescription extends AbstractXmlStatusTest<Description>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDescription.class);
	
	public TestXmlDescription(){super(Description.class);}
	public static Description create(boolean withChildren){return (new TestXmlDescription()).build(withChildren);} 
    public static Description create(boolean withChildren, String key, String description){return (new TestXmlDescription()).build(withChildren,"myKey","myValue");}
    
    public Description build(boolean withChildren){return build(withChildren,"myKey","myValue");}
    public Description build(boolean withChildren, String key, String description)
    {
    	Description xml = new Description();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlDescription test = new TestXmlDescription();
		test.saveReferenceXml();
    }
}