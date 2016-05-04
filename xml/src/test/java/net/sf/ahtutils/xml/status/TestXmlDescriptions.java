package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDescriptions extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLang.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"descriptions.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Descriptions actual = create();
    	Descriptions expected = (Descriptions)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Descriptions.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Descriptions create(){return create(true);}
    public static Descriptions create(boolean withChilds)
    {
    	Descriptions xml = new Descriptions();
    	
    	if(withChilds)
    	{
    		xml.getDescription().add(TestXmlDescription.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlDescriptions.initFiles();	
		TestXmlDescriptions test = new TestXmlDescriptions();
		test.save();
    }
}