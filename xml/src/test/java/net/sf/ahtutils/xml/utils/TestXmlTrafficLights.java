package net.sf.ahtutils.xml.utils;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTrafficLights extends AbstractXmlUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTrafficLights.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, TrafficLights.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	TrafficLights actual = create(true);
    	TrafficLights expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), TrafficLights.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static TrafficLights create(boolean withChilds)
    {
    	TrafficLights xml = new TrafficLights();
    	
    	if(withChilds)
    	{
    		xml.getTrafficLight().add(TestXmlTrafficLight.create(false));
    		xml.getTrafficLight().add(TestXmlTrafficLight.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTrafficLights.initJaxb();
		TestXmlTrafficLights.initFiles();	
		TestXmlTrafficLights test = new TestXmlTrafficLights();
		test.save();
    }
}