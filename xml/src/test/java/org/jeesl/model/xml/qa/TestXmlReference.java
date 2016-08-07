package org.jeesl.model.xml.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.qa.Reference;
import net.sf.ahtutils.xml.qa.Test;

public class TestXmlReference extends AbstractXmlQaTest<Reference>
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	public TestXmlReference(){super(Reference.class);}
	public static Reference create(boolean withChildren){return (new TestXmlReference()).build(withChildren);}   
    
    public Reference build(boolean withChildren)
    {
    	Reference xml = new Reference();
    	
    	xml.setValue("myReference");

    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlReference test = new TestXmlReference();
		test.saveReferenceXml();
    }
}