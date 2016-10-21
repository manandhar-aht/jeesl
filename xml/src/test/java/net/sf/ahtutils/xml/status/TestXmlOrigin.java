package net.sf.ahtutils.xml.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.AbstractXmlStatusTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOrigin extends AbstractXmlStatusTest<Origin>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOrigin.class);
	
	public TestXmlOrigin(){super(Origin.class);}
	public static Origin create(boolean withChildren){return (new TestXmlOrigin()).build(withChildren);} 
    
    public Origin build(boolean withChilds)
    {
    	Origin xml = new Origin();
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
		JeeslXmlTestBootstrap.init();
		TestXmlOrigin test = new TestXmlOrigin();
		test.saveReferenceXml();
    }
}