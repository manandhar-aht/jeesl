package org.jeesl.model.xml.monitoring;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.monitoring.Monitoring;

public class TestXmlMonitoring extends AbstractXmlMonitoringTest<Monitoring>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMonitoring.class);
	
	public TestXmlMonitoring(){super(Monitoring.class);}
	public static Monitoring create(boolean withChildren){return (new TestXmlMonitoring()).build(withChildren);}
    
    public Monitoring build(boolean withChilds)
    {
    	Monitoring xml = new Monitoring();
    	xml.setId(123);
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(false),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlMonitoring test = new TestXmlMonitoring();
		test.saveReferenceXml();
    }
}