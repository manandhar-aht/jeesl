package org.jeesl.model.xml.system.util;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.utils.TrafficLights;

public class TestXmlTrafficLights extends AbstractXmlUtilsTest<TrafficLights>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTrafficLights.class);
	
	public TestXmlTrafficLights(){super(TrafficLights.class);}
	public static TrafficLights create(boolean withChildren){return (new TestXmlTrafficLights()).build(withChildren);}
    
    public TrafficLights build(boolean withChilds)
    {
    	TrafficLights xml = new TrafficLights();
    	
    	if(withChilds)
    	{
    		xml.getTrafficLight().add(TestXmlTrafficLight.create(false));
    		xml.getTrafficLight().add(TestXmlTrafficLight.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlTrafficLights test = new TestXmlTrafficLights();
		test.saveReferenceXml();
    }
}