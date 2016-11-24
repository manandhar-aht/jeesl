package org.jeesl.model.xml.jeesl;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.survey.TestXmlData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuerySurvey extends AbstractXmlJeeslTest<QuerySurvey>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuerySurvey.class);
	
	public TestXmlQuerySurvey(){super(QuerySurvey.class);}
	public static QuerySurvey create(boolean withChildren){return (new TestXmlQuerySurvey()).build(withChildren);}
    
    public QuerySurvey build(boolean withChilds)
    {
    	QuerySurvey xml = new QuerySurvey();
        	
    	if(withChilds)
    	{
    		xml.setData(TestXmlData.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlQuerySurvey test = new TestXmlQuerySurvey();
		test.saveReferenceXml();
    }
}