package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViews extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViews.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"views.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Views actual = create();
    	Views expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Views.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Views create(){return create(true);}
    public static Views create(boolean withChilds)
    {
    	Views xml = new Views();
    	
    	if(withChilds)
    	{
    		xml.getView().add(TestXmlView.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlViews.initFiles();	
		TestXmlViews test = new TestXmlViews();
		test.save();
    }
}