package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlXlsSheet extends AbstractXmlReportTest<XlsSheet>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsSheet.class);
	
	public TestXmlXlsSheet(){super(XlsSheet.class);}
	public static XlsSheet create(boolean withChildren){return (new TestXmlXlsSheet()).build(withChildren);}
    
    public XlsSheet build(boolean withChildren)
    {
    	XlsSheet xml = new XlsSheet();
    	xml.setQuery("myQuery");
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.getXlsColumn().add(TestXmlXlsColumn.create(false));xml.getXlsColumn().add(TestXmlXlsColumn.create(false));
    		xml.getXlsColumns().add(TestXmlXlsColumns.create(false));xml.getXlsColumns().add(TestXmlXlsColumns.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlXlsSheet test = new TestXmlXlsSheet();
		test.saveReferenceXml();
    }
}