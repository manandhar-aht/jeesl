package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlXlsWorkbook extends AbstractXmlReportTest<XlsWorkbook>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsWorkbook.class);
	
	public TestXmlXlsWorkbook(){super(XlsWorkbook.class);}
	public static XlsWorkbook create(boolean withChildren){return (new TestXmlXlsWorkbook()).build(withChildren);} 
    
    public XlsWorkbook build(boolean withChildren)
    {
    	XlsWorkbook xml = new XlsWorkbook();
    	xml.setCode("myCode");
    	
    	if(withChildren)
    	{
    		xml.setXlsSheets(TestXmlXlsSheets.create(false));
    		xml.getXlsSheet().add(TestXmlXlsSheet.create(false));
    		xml.getXlsSheet().add(TestXmlXlsSheet.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlXlsWorkbook test = new TestXmlXlsWorkbook();
		test.saveReferenceXml();
    }
}