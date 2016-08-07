package net.sf.ahtutils.xml.utils;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlProperty extends AbstractXmlUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlProperty.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Property.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Property actual = create(true);
    	Property expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Property.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Property create(boolean withChilds)
    {
    	Property xml = new Property();
        xml.setKey("myKey");
        xml.setValue("myValue");
        xml.setFrozen(true);
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlProperty.initJaxb();
		TestXmlProperty.initFiles();	
		TestXmlProperty test = new TestXmlProperty();
		test.save();
    }
}