package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlXlsDefinition extends AbstractXmlReportTest<XlsDefinition>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsDefinition.class);
	
	public TestXmlXlsDefinition(){super(XlsDefinition.class);}
	public static XlsDefinition create(boolean withChildren){return (new TestXmlXlsDefinition()).build(withChildren);}
    
    public XlsDefinition build(boolean withChildren)
    {
    	XlsDefinition xml = new XlsDefinition();
    	
    	if(withChildren)
    	{
    		xml.getXlsWorkbook().add(TestXmlXlsWorkbook.create(false));
    		xml.getXlsWorkbook().add(TestXmlXlsWorkbook.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlXlsDefinition test = new TestXmlXlsDefinition();
		test.saveReferenceXml();
    }
}