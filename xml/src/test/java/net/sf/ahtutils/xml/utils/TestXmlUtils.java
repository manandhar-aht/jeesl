package net.sf.ahtutils.xml.utils;

import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.access.TestXmlCategory;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUtils extends AbstractXmlUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUtils.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Utils.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Utils actual = create(true);
    	Utils expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Utils.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Utils create(boolean withChilds)
    {
    	Utils xml = new Utils();
        	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));  		
    		xml.getProperty().add(TestXmlProperty.create(false));xml.getProperty().add(TestXmlProperty.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlUtils.initJaxb();
		TestXmlUtils.initFiles();	
		TestXmlUtils test = new TestXmlUtils();
		test.save();
    }
}