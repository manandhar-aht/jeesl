package org.jeesl.model.xml.system.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Implementation;

public class TestXmlImplementation extends AbstractXmlStatusTest<Implementation>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlImplementation.class);
	
	public TestXmlImplementation(){super(Implementation.class);}
	public static Implementation create(boolean withChildren){return (new TestXmlImplementation()).build(withChildren);} 
    
    public Implementation build(boolean withChilds)
    {
    	Implementation xml = new Implementation();
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
		TestXmlImplementation test = new TestXmlImplementation();
		test.saveReferenceXml();
    }
}