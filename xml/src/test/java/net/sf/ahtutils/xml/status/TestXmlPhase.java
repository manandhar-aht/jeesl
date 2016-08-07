package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPhase extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPhase.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Phase.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Phase actual = create(true);
    	Phase expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Phase.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Phase create(boolean withChilds)
    {
    	Phase xml = new Phase();
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
			
		TestXmlPhase.initFiles();	
		TestXmlPhase test = new TestXmlPhase();
		test.save();
    }
}