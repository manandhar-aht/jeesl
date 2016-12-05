package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.TestXmlDescriptions;
import org.jeesl.model.xml.system.status.TestXmlLangs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.XlsColumnGroup;

public class TestXmlXlsColumnGroup extends AbstractXmlReportTest<XlsColumnGroup>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsColumnGroup.class);
	
	public TestXmlXlsColumnGroup(){super(XlsColumnGroup.class);}
	public static XlsColumnGroup create(boolean withChildren){return (new TestXmlXlsColumnGroup()).build(withChildren);}   
    
    public XlsColumnGroup build(boolean withChildren)
    {
    	XlsColumnGroup xml = new XlsColumnGroup();
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlXlsColumnGroup test = new TestXmlXlsColumnGroup();
		test.saveReferenceXml();
    }
}