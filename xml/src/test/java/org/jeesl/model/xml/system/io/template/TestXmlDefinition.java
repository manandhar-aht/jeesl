package org.jeesl.model.xml.system.io.template;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlType;

public class TestXmlDefinition extends AbstractXmlTemplateTest<Definition>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDefinition.class);
	
	public TestXmlDefinition(){super(Definition.class);}
	public static Definition create(boolean withChildren){return (new TestXmlDefinition()).build(withChildren);} 
    
    public Definition build(boolean withChilds)
    {
    	Definition xml = new Definition();
    	xml.setId(123);
    	xml.setPosition(2);
    	xml.setVisible(true);
    	
    	if(withChilds)
    	{
    		xml.setType(TestXmlType.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlDefinition test = new TestXmlDefinition();
		test.saveReferenceXml();
    }
}