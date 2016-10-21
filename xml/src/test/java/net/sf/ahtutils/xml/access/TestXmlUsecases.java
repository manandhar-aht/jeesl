package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUsecases extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUsecases.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"usecases.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Usecases actual = create();
    	Usecases expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Usecases.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Usecases create(){return create(true);}
    public static Usecases create(boolean withChilds)
    {
    	Usecases xml = new Usecases();
    	
    	if(withChilds)
    	{
    		xml.getUsecase().add(TestXmlUsecase.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlUsecases.initFiles();	
		TestXmlUsecases test = new TestXmlUsecases();
		test.save();
    }
}