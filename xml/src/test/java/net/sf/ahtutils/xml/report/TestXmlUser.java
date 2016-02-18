package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlUser extends AbstractXmlReportTest<User>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
	
	public TestXmlUser(){super(User.class);}
	public static User create(boolean withChildren){return (new TestXmlUser()).build(withChildren);} 
    
    public User build(boolean withChildren)
    {
    	User xml = new User();
    	xml.setValue("myUser");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlUser test = new TestXmlUser();
		test.saveReferenceXml();
    }
}