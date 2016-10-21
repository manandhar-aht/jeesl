package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSatisfaction extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSatisfaction.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Satisfaction.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Satisfaction actual = create(true);
    	Satisfaction expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Satisfaction.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Satisfaction create(boolean withChilds)
    {
    	Satisfaction xml = new Satisfaction();
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
			
		TestXmlSatisfaction.initFiles();	
		TestXmlSatisfaction test = new TestXmlSatisfaction();
		test.save();
    }
}