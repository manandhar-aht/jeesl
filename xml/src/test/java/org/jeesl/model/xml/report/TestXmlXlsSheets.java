package org.jeesl.model.xml.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.XlsSheets;

public class TestXmlXlsSheets extends AbstractXmlReportTest<XlsSheets>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsSheets.class);
	
	public TestXmlXlsSheets(){super(XlsSheets.class);}
	public static XlsSheets create(boolean withChildren){return (new TestXmlXlsSheets()).build(withChildren);}
    
    public XlsSheets build(boolean withChildren)
    {
    	XlsSheets xml = new XlsSheets();
    	xml.setQuery("myQuery");
    	
    	if(withChildren)
    	{
    		xml.getXlsSheet().add(TestXmlXlsSheet.create(false));
    		xml.getXlsSheet().add(TestXmlXlsSheet.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlXlsSheets test = new TestXmlXlsSheets();
		test.saveReferenceXml();
    }
}