package org.jeesl.model.xml.system.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Descriptions;

public class TestXmlDescriptions extends AbstractXmlStatusTest<Descriptions>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLang.class);
	
	public TestXmlDescriptions(){super(Descriptions.class);}
	public static Descriptions create(boolean withChildren){return (new TestXmlDescriptions()).build(withChildren);} 
    
    public Descriptions build(boolean withChilds)
    {
    	Descriptions xml = new Descriptions();
    	
    	if(withChilds)
    	{
    		xml.getDescription().add(TestXmlDescription.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();	
		TestXmlDescriptions test = new TestXmlDescriptions();
		test.saveReferenceXml();
    }
}