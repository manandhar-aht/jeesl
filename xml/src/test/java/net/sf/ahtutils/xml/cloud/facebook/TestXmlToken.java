package net.sf.ahtutils.xml.cloud.facebook;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.AbstractXmlTest;
import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlToken extends AbstractXmlFacebookTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlToken.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"token");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Token actual = create();
    	Token expected = (Token)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Token.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Token create(){return create(false);}
    public static Token create(boolean withChilds)
    {
    	Token xml = new Token();
    	xml.setCode("myCode");
    	xml.setValue("myValue");
    	xml.setExpires(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setExpiresIn(10);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}

    public static void main(String[] args)
    {
        JeeslXmlTestBootstrap.init();
        AbstractXmlTest.initJaxb();

        TestXmlToken.initFiles();
        TestXmlToken test = new TestXmlToken();
        test.save();
    }
}