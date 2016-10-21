package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.TestXmlTracked;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDeclaration extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDeclaration.class);
	
	@BeforeClass public static void initFiles() {fXml = new File(rootDir,Declaration.class.getSimpleName()+".xml");}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Declaration actual = create(true);
    	Declaration expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Declaration.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Declaration create(boolean withChilds)
    {
    	Declaration xml = new Declaration();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		
    		xml.getTracked().add(TestXmlTracked.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlDeclaration.initFiles();	
		TestXmlDeclaration test = new TestXmlDeclaration();
		test.save();
    }
}