package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlScopes extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScopes.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Scopes.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Scopes actual = create(true);
    	Scopes expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Scopes.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Scopes create(boolean withChilds)
    {
    	Scopes xml = new Scopes();
    	xml.setGroup("myGroup");
    	xml.setSize(3);
    	
    	if(withChilds)
    	{
    		xml.getScope().add(TestXmlScope.create(false));
    		xml.getScope().add(TestXmlScope.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlScopes.initFiles();	
		TestXmlScopes test = new TestXmlScopes();
		test.save();
    }
}