package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Label;

public class TestXmlLabel extends AbstractXmlReportTest<Label>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLabel.class);
	
	public TestXmlLabel(){super(Label.class);}
	public static Label create(boolean withChildren){return (new TestXmlLabel()).build(withChildren);}   
    
    public Label build(boolean withChildren)
    {
    	Label xml = new Label();
    	xml.setScope("myScope");
    	xml.setKey("myKey");
    	xml.setValue("myLabel");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlLabel test = new TestXmlLabel();
		test.saveReferenceXml();
    }
}