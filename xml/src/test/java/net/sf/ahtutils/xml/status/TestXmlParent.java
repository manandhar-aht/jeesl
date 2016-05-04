package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlParent extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlParent.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Parent.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Parent actual = create(true);
    	Parent expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Parent.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Parent create(boolean withChilds)
    {
    	Parent xml = new Parent();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setPosition(2);
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlParent.initFiles();	
		TestXmlParent test = new TestXmlParent();
		test.save();
    }
}