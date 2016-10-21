package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGroups extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGroups.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"groups.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Groups actual = create();
    	Groups expected = (Groups)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Groups.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Groups create(){return create(true);}
    public static Groups create(boolean withChilds)
    {
    	Groups xml = new Groups();
    	
    	if(withChilds)
    	{
    		xml.getGroup().add(TestXmlGroup.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlGroups.initFiles();	
		TestXmlGroups test = new TestXmlGroups();
		test.save();
    }
}