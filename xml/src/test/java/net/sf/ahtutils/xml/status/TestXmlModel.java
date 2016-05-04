package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlModel extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlModel.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Model.class);}
	
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Model actual = create(true);
    	Model expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Model.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Model create(boolean withChilds)
    {
    	Model xml = new Model();
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
			
		TestXmlModel.initFiles();	
		TestXmlModel test = new TestXmlModel();
		test.save();
    }
}