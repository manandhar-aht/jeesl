package org.jeesl.model.xml.qa;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.qa.Actual;
import net.sf.ahtutils.xml.qa.Test;

public class TestXmlActual extends AbstractXmlQaTest<Actual>
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	public TestXmlActual(){super(Actual.class);}
	public static Actual create(boolean withChildren){return (new TestXmlActual()).build(withChildren);}
    
    public Actual build(boolean withChilds)
    {
    	Actual xml = new Actual();
    	xml.setValue("myActual");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlActual test = new TestXmlActual();
		test.saveReferenceXml();
    }
}