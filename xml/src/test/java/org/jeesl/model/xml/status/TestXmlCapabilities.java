package org.jeesl.model.xml.status;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Capabilities;

public class TestXmlCapabilities extends AbstractXmlStatusTest<Capabilities>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCapabilities.class);
	
	public TestXmlCapabilities(){super(Capabilities.class);}
	public static Capabilities create(boolean withChildren){return (new TestXmlCapabilities()).build(withChildren);}   
    
    public Capabilities build(boolean withChilds)
    {
    	Capabilities xml = new Capabilities();
    	    	
    	if(withChilds)
    	{
    		xml.getCapability().add(TestXmlCapability.create(false));
    		xml.getCapability().add(TestXmlCapability.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlCapabilities test = new TestXmlCapabilities();
		test.saveReferenceXml();
    }
}