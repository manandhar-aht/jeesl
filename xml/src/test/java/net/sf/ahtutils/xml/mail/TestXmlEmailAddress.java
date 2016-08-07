package net.sf.ahtutils.xml.mail;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEmailAddress extends AbstractXmlMailTest<EmailAddress>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEmailAddress.class);
	
	public TestXmlEmailAddress(){super(EmailAddress.class);}
	public static EmailAddress create(boolean withChildren){return (new TestXmlEmailAddress()).build(withChildren);}
	
    public EmailAddress build(boolean withChilds)
    {
    	EmailAddress xml = new EmailAddress();
    	xml.setName("myName");
    	xml.setEmail("my@e.mail");
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlEmailAddress test = new TestXmlEmailAddress();
		test.saveReferenceXml();
    }
}