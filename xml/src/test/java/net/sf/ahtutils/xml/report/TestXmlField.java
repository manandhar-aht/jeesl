package net.sf.ahtutils.xml.report;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlField extends AbstractXmlReportTest<Field>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlField.class);
	
	public TestXmlField(){super(Field.class);}
	public static Field create(boolean withChildren){return (new TestXmlField()).build(withChildren);}   
    
    public Field build(boolean withChildren)
    {
    	Field field = new Field();
    	field.setExpression("/report/title");
    	field.setType("field");
    	field.setName("title");
    	return field;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlField test = new TestXmlField();
		test.saveReferenceXml();
    }
}