package org.jeesl.model.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Security;

public class TestXmlSecurity extends AbstractXmlSecurityTest<Security>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSecurity.class);
	
	public TestXmlSecurity(){super(Security.class);}
	public Security create(boolean withChildren){return (new TestXmlSecurity()).build(withChildren);}
    
    public Security build(boolean withChilds)
    {
    	Security xml = new Security();
    	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getCategory().add(TestXmlCategory.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlSecurity test = new TestXmlSecurity();
		test.saveReferenceXml();
    }
}