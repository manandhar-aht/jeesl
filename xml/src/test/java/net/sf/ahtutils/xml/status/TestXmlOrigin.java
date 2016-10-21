package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOrigin extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOrigin.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Origin.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Origin actual = create(true);
    	Origin expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Origin.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Origin create(boolean withChilds)
    {
    	Origin xml = new Origin();
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
		JeeslXmlTestBootstrap.init();
			
		TestXmlOrigin.initFiles();	
		TestXmlOrigin test = new TestXmlOrigin();
		test.save();
    }
}