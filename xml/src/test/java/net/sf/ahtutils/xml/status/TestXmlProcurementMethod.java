package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlProcurementMethod extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlProcurementMethod.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, ProcurementMethod.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	ProcurementMethod actual = create(true);
    	ProcurementMethod expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ProcurementMethod.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static ProcurementMethod create(boolean withChilds)
    {
    	ProcurementMethod xml = new ProcurementMethod();
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
			
		TestXmlProcurementMethod.initFiles();	
		TestXmlProcurementMethod test = new TestXmlProcurementMethod();
		test.save();
    }
}