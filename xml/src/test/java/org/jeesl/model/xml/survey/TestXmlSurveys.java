package org.jeesl.model.xml.survey;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Surveys;

public class TestXmlSurveys extends AbstractXmlSurveyTest<Surveys>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSurveys.class);
	
	public TestXmlSurveys(){super(Surveys.class);}
	public static Surveys create(boolean withChildren){return (new TestXmlSurveys()).build(withChildren);}   
    
    public Surveys build(boolean withChilds)
    {
    	Surveys xml = new Surveys();
    	if(withChilds)
    	{    		
    		xml.getSurvey().add(TestXmlSurvey.create(false));
    		xml.getSurvey().add(TestXmlSurvey.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlSurveys test = new TestXmlSurveys();
		test.saveReferenceXml();
    }
}