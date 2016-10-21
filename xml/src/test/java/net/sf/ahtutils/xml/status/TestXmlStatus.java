package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStatus extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStatus.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"status2.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Status actual = create(true);
    	Status expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Status.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Status create(boolean withChilds)
    {
    	Status xml = new Status();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setSymbol("mySymbol");
    	xml.setImage("test/green.png");
    	xml.setStyle("myStyle");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.getLang().add(TestXmlLang.create(false));
    		xml.setTransistions(TestXmlTransistions.create(false));
    		xml.setParent(TestXmlParent.create(false));
    		
    		xml.getTracked().add(TestXmlTracked.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlStatus.initFiles();	
		TestXmlStatus test = new TestXmlStatus();
		test.save();
    }
}