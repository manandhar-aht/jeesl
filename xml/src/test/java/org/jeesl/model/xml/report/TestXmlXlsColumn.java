package org.jeesl.model.xml.report;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlXlsColumn extends AbstractXmlReportTest<XlsColumn>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsColumn.class);
	
	public TestXmlXlsColumn(){super(XlsColumn.class);}
	public static XlsColumn create(boolean withChildren){return (new TestXmlXlsColumn()).build(withChildren);}   
    
    public XlsColumn build(boolean withChildren)
    {
    	XlsColumn xml = new XlsColumn();
    	
    	xml.setColumn("A");
    	xml.setLabel("myLabel");
    	xml.setRequired(true);
    	xml.setExample("myExample");
    	xml.setAutoWidth(false);
    	xml.setWidth(2);
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setXlsTransformation(TestXmlXlsTransformation.create(false));
    		xml.setDataAssociation(TestXmlDataAssociation.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlXlsColumn test = new TestXmlXlsColumn();
		test.saveReferenceXml();
    }
}