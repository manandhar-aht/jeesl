package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.TestXmlDescriptions;
import org.jeesl.model.xml.system.status.TestXmlLangs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Row;

public class TestXmlRow extends AbstractXmlReportTest<Row>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRow.class);
	
	public TestXmlRow(){super(Row.class);}
	public static Row create(boolean withChildren){return (new TestXmlRow()).build(withChildren);}   
    
    public Row build(boolean withChildren)
    {
    	Row xml = new Row();
    	xml.setCode("myCode");
    	xml.setPosition(1);
    	xml.setVisible(true);
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setOffset(TestXmlOffset.create(false));
    		xml.setQueries(TestXmlQueries.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlRow test = new TestXmlRow();
		test.saveReferenceXml();
    }
}