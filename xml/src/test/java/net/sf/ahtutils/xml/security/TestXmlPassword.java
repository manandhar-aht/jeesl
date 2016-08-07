package net.sf.ahtutils.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPassword extends AbstractXmlSecurityTest<Password>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPassword.class);
	
	public TestXmlPassword(){super(Password.class);}
	public Password create(boolean withChildren){return (new TestXmlPassword()).build(withChildren);}
    
    public Password build(boolean withChilds)
    {
    	Password xml = new Password();
    	xml.setValid(true);
    	if(withChilds)
    	{
    		xml.getRule().add(TestXmlRule.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlPassword test = new TestXmlPassword();
		test.saveReferenceXml();
    }
}