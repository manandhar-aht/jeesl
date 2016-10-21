package org.jeesl.model.xml.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;

public class TestXmlReport extends AbstractXmlReportTest<Report>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlReport.class);
	
	public TestXmlReport(){super(Report.class);}
	public static Report create(boolean withChildren){return (new TestXmlReport()).build(withChildren);}
    
    public Report build(boolean withChildren)
    {
    	Report xml = new Report();
    	xml.setId("myId");
    	xml.setDir("testDir");
    	xml.setExample("testExampleXmlFile");
    	xml.getMedia().add(TestXmlMedia.create(false));
    	xml.getMedia().add(TestXmlMedia.create(false));
    	xml.setLtr(true);
    	xml.setRtl(false);
    	xml.setTest("myTestClass");
    	
    	if(withChildren)
    	{
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlReport test = new TestXmlReport();
		test.saveReferenceXml();
    }
}