package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Style;

public class TestXmlStyle extends AbstractXmlReportTest<Style>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyle.class);
	
	public TestXmlStyle(){super(Style.class);}
	public static Style create(boolean withChildren){return (new TestXmlStyle()).build(withChildren);} 
    
    public Style build(boolean withChildren)
    {
    	Style xml = new Style();
    	xml.setId(123);
    	xml.setType("myType");
    	xml.setCode("myCode");
    	xml.setPosition(1);
    	xml.setVisible(true);
    	
    	if(withChildren)
    	{
    		xml.setLayout(TestXmlLayout.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlStyle test = new TestXmlStyle();
		test.saveReferenceXml();
    }
}