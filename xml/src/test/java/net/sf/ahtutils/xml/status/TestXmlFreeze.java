package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFreeze extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFreeze.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Freeze.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Freeze actual = create(true);
    	Freeze expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Freeze.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Freeze create(boolean withChilds)
    {
    	Freeze xml = new Freeze();
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
    		
    		xml.getTracked().add(TestXmlTracked.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlFreeze.initFiles();	
		TestXmlFreeze test = new TestXmlFreeze();
		test.save();
    }
}