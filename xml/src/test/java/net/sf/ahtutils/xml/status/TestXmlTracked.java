package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.security.TestXmlUser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTracked extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTracked.class);
	
	@BeforeClass public static void initFiles() {fXml = new File(rootDir,Tracked.class.getSimpleName()+".xml");}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Tracked actual = create(true);
    	Tracked expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Tracked.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Tracked create(boolean withChilds)
    {
    	Tracked xml = new Tracked();
    	xml.setRecord(TestXmlTracked.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setUser(TestXmlUser.create(false));
    		
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setStatement(TestXmlStatement.create(false));
    		xml.setDeclaration(TestXmlDeclaration.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlTracked.initFiles();	
		TestXmlTracked test = new TestXmlTracked();
		test.save();
    }
}