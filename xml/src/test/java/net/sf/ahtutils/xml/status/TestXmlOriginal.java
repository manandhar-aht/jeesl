package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOriginal extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOriginal.class);
	
	@BeforeClass public static void initFiles(){fXml = new File(rootDir,Original.class.getSimpleName()+".xml");}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Original actual = create(true);
    	Original expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Original.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Original create(boolean withChilds)
    {
    	Original xml = new Original();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlOriginal.initFiles();	
		TestXmlOriginal test = new TestXmlOriginal();
		test.save();
    }
}