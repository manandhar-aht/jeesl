package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCategories extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCategories.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Categories.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Categories actual = create(true);
    	Categories expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Categories.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Categories create(boolean withChilds)
    {
    	Categories xml = new Categories();
    	xml.setGroup("myGroup");
    	xml.setSize(3);
    	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getCategory().add(TestXmlCategory.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlCategories.initFiles();	
		TestXmlCategories test = new TestXmlCategories();
		test.save();
    }
}