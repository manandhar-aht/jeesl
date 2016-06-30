package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTransistions extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTransistions.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"transistions.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Transistions actual = create(true);
    	Transistions expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Transistions.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Transistions create(boolean withChilds)
    {
    	Transistions xml = new Transistions();
    	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));
    		xml.getStatus().add(TestXmlStatus.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTransistions.initFiles();	
		TestXmlTransistions test = new TestXmlTransistions();
		test.save();
    }
}