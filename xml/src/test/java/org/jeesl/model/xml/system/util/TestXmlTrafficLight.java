package org.jeesl.model.xml.system.util;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.status.TestXmlScope;
import net.sf.ahtutils.xml.utils.TrafficLight;

public class TestXmlTrafficLight extends AbstractXmlUtilsTest<TrafficLight>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTrafficLight.class);
	
	public TestXmlTrafficLight(){super(TrafficLight.class);}
	public static TrafficLight create(boolean withChildren){return (new TestXmlTrafficLight()).build(withChildren);}
    
    public TrafficLight build(boolean withChilds)
    {
    	TrafficLight xml = new TrafficLight();
        xml.setId(123);
        xml.setColorText("ff00ff");
        xml.setColorBackground("00ff00");
    	
    	if(withChilds)
    	{
    		xml.setScope(TestXmlScope.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTrafficLight test = new TestXmlTrafficLight();
		test.saveReferenceXml();
    }
}