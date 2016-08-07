package net.sf.ahtutils.xml.utils;

import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.status.TestXmlScope;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTrafficLight extends AbstractXmlUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTrafficLight.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, TrafficLight.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	TrafficLight actual = create(true);
    	TrafficLight expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), TrafficLight.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static TrafficLight create(boolean withChilds)
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
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTrafficLight.initJaxb();
		TestXmlTrafficLight.initFiles();	
		TestXmlTrafficLight test = new TestXmlTrafficLight();
		test.save();
    }
}