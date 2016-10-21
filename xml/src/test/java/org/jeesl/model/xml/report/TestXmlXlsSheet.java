package org.jeesl.model.xml.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.XlsSheet;

public class TestXmlXlsSheet extends AbstractXmlReportTest<XlsSheet>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsSheet.class);
	
	public TestXmlXlsSheet(){super(XlsSheet.class);}
	public static XlsSheet create(boolean withChildren){return (new TestXmlXlsSheet()).build(withChildren);}
    
    public XlsSheet build(boolean withChildren)
    {
    	XlsSheet xml = new XlsSheet();
    	xml.setQuery("myQuery");
    	xml.setPrimaryKey("myPK");
    	
    	if(withChildren)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlXlsSheet test = new TestXmlXlsSheet();
		test.saveReferenceXml();
    }
}