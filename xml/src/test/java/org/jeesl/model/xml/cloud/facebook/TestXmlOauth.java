package org.jeesl.model.xml.cloud.facebook;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.cloud.facebook.Oauth;

public class TestXmlOauth extends AbstractXmlFacebookTest<Oauth>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlApp.class);
	
	public TestXmlOauth(){super(Oauth.class);}
	public static Oauth create(boolean withChildren){return (new TestXmlOauth()).build(withChildren);}
    
    public Oauth build(boolean withChilds)
    {
    	Oauth xml = new Oauth();
    	xml.setToken("myToken");
    	return xml;
    }
    
    public static void main(String[] args)
    {
        JeeslXmlTestBootstrap.init();
        TestXmlOauth test = new TestXmlOauth();
		test.saveReferenceXml();
    }
}