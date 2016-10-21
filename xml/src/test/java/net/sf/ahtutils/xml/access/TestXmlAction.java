package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAction extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAction.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"action.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Action actual = create();
    	Action expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Action.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Action create(){return create(true);}
    public static Action create(boolean withChilds)
    {
    	Action xml = new Action();
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlAction.initFiles();	
		TestXmlAction test = new TestXmlAction();
		test.save();
    }
}