package net.sf.ahtutils.xml.cloud.facebook;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUser extends AbstractXmlFacebookTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"user.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	User actual = create();
    	User expected = (User)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static User create(){return create(false);}
    public static User create(boolean withChilds)
    {
    	User xml = new User();
    	xml.setId("1");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		
		TestXmlUser.initFiles();
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}