package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEvaluationMethod extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEvaluationMethod.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, EvaluationMethod.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	EvaluationMethod actual = create(true);
    	EvaluationMethod expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), EvaluationMethod.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static EvaluationMethod create(boolean withChilds)
    {
    	EvaluationMethod xml = new EvaluationMethod();
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
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlEvaluationMethod.initFiles();	
		TestXmlEvaluationMethod test = new TestXmlEvaluationMethod();
		test.save();
    }
}