package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDescription extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDescription.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"description.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Description actual = create();
    	Description expected = (Description)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Description.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Description create(){return create(true);}
    public static Description create(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Description create(boolean withChilds, String key, String description)
    {
    	Description xml = new Description();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlDescription.initFiles();	
		TestXmlDescription test = new TestXmlDescription();
		test.save();
    }
}