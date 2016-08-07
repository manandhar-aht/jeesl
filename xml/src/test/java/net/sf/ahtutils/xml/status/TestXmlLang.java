package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLang extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLang.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"lang.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Lang actual = create();
    	Lang expected = (Lang)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Lang.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Lang create(){return create(true);}
    public static Lang create(boolean withChilds){return create(withChilds,"myKey","myTranslation");}
    public static Lang create(boolean withChilds, String key, String translation)
    {
    	Lang xml = new Lang();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setTranslation(translation);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlLang.initFiles();	
		TestXmlLang test = new TestXmlLang();
		test.save();
    }
}