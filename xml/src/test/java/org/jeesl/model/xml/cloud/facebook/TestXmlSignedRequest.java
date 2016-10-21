package org.jeesl.model.xml.cloud.facebook;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.xml.cloud.facebook.SignedRequest;

public class TestXmlSignedRequest extends AbstractXmlFacebookTest<SignedRequest>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSignedRequest.class);
	
	public TestXmlSignedRequest(){super(SignedRequest.class);}
	public static SignedRequest create(boolean withChildren){return (new TestXmlSignedRequest()).build(withChildren);}
    
    public SignedRequest build(boolean withChilds)
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

	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlSignedRequest test = new TestXmlSignedRequest();
		test.saveReferenceXml();
    }
}