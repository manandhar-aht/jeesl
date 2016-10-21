package net.sf.ahtutils.xml.cloud.facebook;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSignedRequest extends AbstractXmlFacebookTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSignedRequest.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"signedRequest");
	}
    
    @Test
    public void testApp() throws FileNotFoundException
    {
    	SignedRequest actual = create();
    	SignedRequest expected = (SignedRequest)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), SignedRequest.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private SignedRequest create(){return create(false);}
    public  SignedRequest create(boolean withChilds)
    {
    	SignedRequest xml = new SignedRequest();
    	xml.setExpires(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setIssuedAt(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setOauth(TestXmlOauth.create(false));
    		xml.setUser(TestXmlUser.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		
		TestXmlSignedRequest.initFiles();
		TestXmlSignedRequest test = new TestXmlSignedRequest();
		test.save();
    }
}