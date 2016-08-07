package net.sf.ahtutils.xml.report;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlJr extends AbstractXmlReportTest<Jr>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlJr.class);
	
	public TestXmlJr(){super(Jr.class);}
	public static Jr create(boolean withChildren){return (new TestXmlJr()).build(withChildren);}   
    
    public Jr build(boolean withChildren)
    {
    	Jr jr = new Jr();
    	jr.setName("testReportName");
    	jr.setType("mr");
    	jr.setVisible(true);
    	return jr;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlJr test = new TestXmlJr();
		test.saveReferenceXml();
    }
}