package org.jeesl.model.xml.cloud.facebook;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.xml.cloud.facebook.Token;

public class TestXmlToken extends AbstractXmlFacebookTest<Token>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlToken.class);
	
	public TestXmlToken(){super(Token.class);}
	public static Token create(boolean withChildren){return (new TestXmlToken()).build(withChildren);}
    
    public Token build(boolean withChilds)
    {
    	Token xml = new Token();
    	xml.setCode("myCode");
    	xml.setValue("myValue");
    	xml.setExpires(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setExpiresIn(10);
    	return xml;
    }
    
    public static void main(String[] args)
    {
        JeeslXmlTestBootstrap.init();
        TestXmlToken test = new TestXmlToken();
		test.saveReferenceXml();
    }
}