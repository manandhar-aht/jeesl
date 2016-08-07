package org.jeesl.model.xml.text;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.text.Question;

public class TestXmlQuestion extends AbstractXmlTextTest<Question>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuestion.class);
	
	public TestXmlQuestion(){super(Question.class);}
	public static Question create(boolean withChildren){return (new TestXmlQuestion()).build(withChildren);}
    
    public Question build(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Question create(boolean withChilds, String key, String description)
    {
    	Question xml = new Question();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlQuestion test = new TestXmlQuestion();
		test.saveReferenceXml();
    }
}