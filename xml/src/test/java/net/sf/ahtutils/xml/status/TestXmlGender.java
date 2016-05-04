package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGender extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGender.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Gender.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Gender actual = create(true);
    	Gender expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Gender.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Gender create(boolean withChilds)
    {
    	Gender xml = new Gender();
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
			
		TestXmlGender.initFiles();	
		TestXmlGender test = new TestXmlGender();
		test.save();
    }
}