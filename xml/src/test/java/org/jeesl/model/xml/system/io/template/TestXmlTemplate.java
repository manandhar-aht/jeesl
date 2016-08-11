package org.jeesl.model.xml.system.io.template;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlTemplate extends AbstractXmlTemplateTest<Template>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplate.class);
	
	public TestXmlTemplate(){super(Template.class);}
	public static Template create(boolean withChildren){return (new TestXmlTemplate()).build(withChildren);} 
    
    public Template build(boolean withChilds)
    {
    	Template xml = new Template();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setPosition(2);
    	xml.setVisible(true);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setTokens(TestXmlTokens.create(false));
    		xml.setDefinitions(TestXmlDefinitions.create(false));
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