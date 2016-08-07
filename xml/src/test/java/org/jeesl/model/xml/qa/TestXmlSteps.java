package org.jeesl.model.xml.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.qa.Steps;
import net.sf.ahtutils.xml.qa.Test;

public class TestXmlSteps extends AbstractXmlQaTest<Steps>
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	public TestXmlSteps(){super(Steps.class);}
	public static Steps create(boolean withChildren){return (new TestXmlSteps()).build(withChildren);}  
    
    public Steps build(boolean withChilds)
    {
    	Steps xml = new Steps();
    	
    	xml.setValue("mySteps");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlSteps test = new TestXmlSteps();
		test.saveReferenceXml();
    }
}