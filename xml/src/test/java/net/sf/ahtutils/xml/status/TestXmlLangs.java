package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLangs extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLangs.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"langs.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Langs actual = create();
    	Langs expected = (Langs)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Langs.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Langs create(){return create(true);}
    public static Langs create(boolean withChilds)
    {
    	Langs xml = new Langs();
    	
    	if(withChilds)
    	{
    		xml.getLang().add(TestXmlLang.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlLangs.initFiles();	
		TestXmlLangs test = new TestXmlLangs();
		test.save();
    }
}