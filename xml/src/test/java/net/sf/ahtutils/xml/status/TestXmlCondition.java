package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCondition extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCondition.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"condition.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Condition actual = create(true);
    	Condition expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Condition.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Condition create(boolean withChilds)
    {
    	Condition xml = new Condition();
    	xml.setId(123);
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
    		xml.getLang().add(TestXmlLang.create(false));
    		xml.setTransistions(TestXmlTransistions.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlCondition.initFiles();	
		TestXmlCondition test = new TestXmlCondition();
		test.save();
    }
}