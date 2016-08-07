package net.sf.ahtutils.xml.utils;

import java.io.FileNotFoundException;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlCiteria extends AbstractXmlUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCiteria.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Criteria.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Criteria actual = create(true);
    	Criteria expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Criteria.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Criteria create(boolean withChilds)
    {
    	Criteria xml = new Criteria();
        xml.setId(123);
        xml.setCode("myCode");
        xml.setType("Boolean");
        xml.setValue("xx");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlCiteria.initJaxb();
		TestXmlCiteria.initFiles();	
		TestXmlCiteria test = new TestXmlCiteria();
		test.save();
    }
}