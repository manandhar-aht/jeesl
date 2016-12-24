package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Queries;

public class TestXmlQueries extends AbstractXmlReportTest<Queries>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQueries.class);
	
	public TestXmlQueries(){super(Queries.class);}
	public static Queries create(boolean withChildren){return (new TestXmlQueries()).build(withChildren);} 
    
    public Queries build(boolean withChildren)
    {
    	Queries xml = new Queries();
    	
    	if(withChildren)
    	{
    		xml.getQuery().add(TestXmlQuery.create(false));
    		xml.getQuery().add(TestXmlQuery.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlQueries test = new TestXmlQueries();
		test.saveReferenceXml();
    }
}