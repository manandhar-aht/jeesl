package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Query;

public class TestXmlQuery extends AbstractXmlReportTest<Query>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	public TestXmlQuery(){super(Query.class);}
	public static Query create(boolean withChildren){return (new TestXmlQuery()).build(withChildren);} 
    
    public Query build(boolean withChildren)
    {
    	Query xml = new Query();
    	xml.setType("myType");
    	xml.setValue("myValue");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlQuery test = new TestXmlQuery();
		test.saveReferenceXml();
    }
}