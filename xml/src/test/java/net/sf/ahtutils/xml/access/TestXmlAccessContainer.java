package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAccessContainer extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAccessContainer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"access.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Access actual = create();
    	Access expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Access.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Access create(){return create(true);}
    public static Access create(boolean withChilds)
    {
    	Access xml = new Access();
    	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlAccessContainer.initFiles();	
		TestXmlAccessContainer test = new TestXmlAccessContainer();
		test.save();
    }
}