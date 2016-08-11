package org.jeesl.model.xml.report;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Template;

public class TestXmlTemplate extends AbstractXmlReportTest<Template>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplate.class);
	
	public TestXmlTemplate(){super(Template.class);}
	public static Template create(boolean withChildren){return (new TestXmlTemplate()).build(withChildren);} 
    
    public Template build(boolean withChildren)
    {
    	Template template = new Template();
    	template.setId("ahtutils-basic-template");
    	template.getElement().add(TestXmlElement.create(false));
    	template.getField().add(TestXmlField.create(false));
    	return template;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlTemplate test = new TestXmlTemplate();
		test.saveReferenceXml();
    }
}