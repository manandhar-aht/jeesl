package org.jeesl.model.xml.monitoring;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.monitoring.Value;

public class TestXmlValue extends AbstractXmlMonitoringTest<Value>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlValue.class);
	
	public TestXmlValue(){super(Value.class);}
	public static Value create(boolean withChildren){return (new TestXmlValue()).build(withChildren);}
    
    public Value build(boolean withChildren)
    {
    	Value xml = new Value();
    	xml.setValue("myValue");
    	xml.setType("myType");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlValue test = new TestXmlValue();
		test.saveReferenceXml();
    }
}