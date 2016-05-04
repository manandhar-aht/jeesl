package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCapabilities extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCapabilities.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Capabilities.class);}
	
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Capabilities actual = create(true);
    	Capabilities expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Capabilities.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Capabilities create(boolean withChilds)
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
			
		TestXmlCapabilities.initFiles();	
		TestXmlCapabilities test = new TestXmlCapabilities();
		test.save();
    }
}