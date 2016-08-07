package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlType extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlType.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"type.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Type actual = create(true);
    	Type expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Type.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Type create(boolean withChilds)
    {
    	Type xml = new Type();
    	xml.setId(123);
    	xml.setKey("myKey");
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setParent(TestXmlParent.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.getLang().add(TestXmlLang.create(false));
    		xml.setTransistions(TestXmlTransistions.create(false));
    		xml.getSubType().add(TestXmlSubType.create(false));xml.getSubType().add(TestXmlSubType.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlType.initFiles();	
		TestXmlType test = new TestXmlType();
		test.save();
    }
}