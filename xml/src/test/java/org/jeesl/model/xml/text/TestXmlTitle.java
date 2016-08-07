package org.jeesl.model.xml.text;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.text.Title;

public class TestXmlTitle extends AbstractXmlTextTest<Title>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTitle.class);
	
	public TestXmlTitle(){super(Title.class);}
	public static Title create(boolean withChildren){return (new TestXmlTitle()).build(withChildren);}
    
    public Title build(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Title create(boolean withChilds, String key, String description)
    {
    	Title xml = new Title();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlTitle test = new TestXmlTitle();
		test.saveReferenceXml();
    }
}