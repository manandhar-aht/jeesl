package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDomain extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDomain.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Domain.class);}
	
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Domain actual = create(true);
    	Domain expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Domain.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Domain create(boolean withChilds)
    {
    	Domain xml = new Domain();
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
			
		TestXmlDomain.initFiles();	
		TestXmlDomain test = new TestXmlDomain();
		test.save();
    }
}