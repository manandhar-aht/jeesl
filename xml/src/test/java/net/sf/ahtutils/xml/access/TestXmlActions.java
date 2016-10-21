package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlActions extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlActions.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"actions.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Actions actual = create();
    	Actions expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Actions.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Actions create(){return create(true);}
    public static Actions create(boolean withChilds)
    {
    	Actions xml = new Actions();
    	
    	if(withChilds)
    	{
    		xml.getAction().add(TestXmlAction.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlActions.initFiles();	
		TestXmlActions test = new TestXmlActions();
		test.save();
    }
}