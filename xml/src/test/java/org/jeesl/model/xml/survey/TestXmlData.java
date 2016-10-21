package org.jeesl.model.xml.survey;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Data;

public class TestXmlData extends AbstractXmlSurveyTest<Data>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	public TestXmlData(){super(Data.class);}
	public static Data create(boolean withChildren){return (new TestXmlData()).build(withChildren);}   

    public Data build(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setId(123);
    	
    	if(withChilds)
    	{    		
    		xml.setSurvey(TestXmlSurvey.create(false));
    		xml.setCorrelation(TestXmlCorrelation.create(false));
    		xml.getAnswer().add(TestXmlAnswer.create(false));xml.getAnswer().add(TestXmlAnswer.create(false));
    	}
    	return xml;
    }
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlData test = new TestXmlData();
		test.saveReferenceXml();
    }
}