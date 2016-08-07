package org.jeesl.model.xml.text;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.text.Hint;

public class TestXmlHint extends AbstractXmlTextTest<Hint>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHint.class);
	
	public TestXmlHint(){super(Hint.class);}
	public static Hint create(boolean withChildren){return (new TestXmlHint()).build(withChildren);}
    
    public Hint build(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Hint create(boolean withChilds, String key, String description)
    {
    	Hint xml = new Hint();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlHint test = new TestXmlHint();
		test.saveReferenceXml();
    }
}