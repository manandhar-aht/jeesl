package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlReport extends AbstractXmlReportTest<Report>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlReport.class);
	
	public TestXmlReport(){super(Report.class);}
	public static Report create(boolean withChildren){return (new TestXmlReport()).build(withChildren);}
    
    public Report build(boolean withChildren)
    {
    	Report report = new Report();
    	report.setId("myId");
    	report.setDir("testDir");
    	report.setExample("testExampleXmlFile");
    	report.getMedia().add(TestXmlMedia.create(false));
    	report.getMedia().add(TestXmlMedia.create(false));
    	report.setLtr(true);
    	report.setRtl(false);
    	return report;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlReport test = new TestXmlReport();
		test.saveReferenceXml();
    }
}