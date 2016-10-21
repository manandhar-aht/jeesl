package org.jeesl.model.xml.cloud.facebook;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.cloud.facebook.User;

public class TestXmlUser extends AbstractXmlFacebookTest<User>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
	
	public TestXmlUser(){super(User.class);}
	public static User create(boolean withChildren){return (new TestXmlUser()).build(withChildren);}
    
    public User build(boolean withChilds)
    {
    	User xml = new User();
    	xml.setId("1");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlUser test = new TestXmlUser();
		test.saveReferenceXml();
    }
}