package net.sf.ahtutils.xml.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlTemplate extends AbstractXmlSecurityTest<Template>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplate.class);
	
	public TestXmlTemplate(){super(Template.class);}
	public static Template create(boolean withChildren){return (new TestXmlTemplate()).build(withChildren);}
    
    public Template build(boolean withChilds)
    {
    	Template xml = new Template();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setVisible(true);
    	xml.setDocumentation(true);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	return xml;
    }

	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlTemplate test = new TestXmlTemplate();
		test.saveReferenceXml();
    }
}