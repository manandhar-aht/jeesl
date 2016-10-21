package org.jeesl.model.xml.cloud.facebook;

import org.jeesl.AbstractXmlTest;
import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlFacebookTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlFacebookTest.class);
	
	public AbstractXmlFacebookTest(Class<T> cXml)
	{
		super(cXml,"cloud/facebook");
	}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		AbstractXmlTest.initJaxb();
		
		new TestXmlApp().saveReferenceXml();
		new TestXmlOauth().saveReferenceXml();
		new TestXmlSignedRequest().saveReferenceXml();
		new TestXmlToken().saveReferenceXml();
		new TestXmlUser().saveReferenceXml();
    }
}