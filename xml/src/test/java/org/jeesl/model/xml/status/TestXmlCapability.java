package org.jeesl.model.xml.status;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Capability;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlCapability extends AbstractXmlStatusTest<Capability>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCapability.class);
	
	public TestXmlCapability(){super(Capability.class);}
	public static Capability create(boolean withChildren){return (new TestXmlCapability()).build(withChildren);}   
    
    public Capability build(boolean withChilds)
    {
    	Capability xml = new Capability();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlCapability test = new TestXmlCapability();
		test.saveReferenceXml();
    }
}