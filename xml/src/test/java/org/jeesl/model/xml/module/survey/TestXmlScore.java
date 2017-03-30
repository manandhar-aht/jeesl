package org.jeesl.model.xml.module.survey;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Score;

public class TestXmlScore extends AbstractXmlSurveyTest<Score>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScore.class);
	
	public TestXmlScore(){super(Score.class);}
	public static Score create(boolean withChildren){return (new TestXmlScore()).build(withChildren);}   

    public Score build(boolean withChilds)
    {
    	Score xml = new Score();
    	xml.setMin(0.1);
    	xml.setMax(9.9);
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlScore test = new TestXmlScore();
		test.saveReferenceXml();
    }
}