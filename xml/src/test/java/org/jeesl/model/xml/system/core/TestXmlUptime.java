package org.jeesl.model.xml.system.core;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlType;
import net.sf.ahtutils.xml.system.Uptime;

public class TestXmlUptime extends AbstractXmlSystemTest<Uptime>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUptime.class);
	
	public TestXmlUptime(){super(Uptime.class);}
	public static Uptime create(boolean withChildren){return (new TestXmlUptime()).build(withChildren);}
    
    public Uptime build(boolean withChilds)
    {
    	Uptime xml = new Uptime();
    	xml.setSince(TestXmlUptime.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setType(TestXmlType.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlUptime test = new TestXmlUptime();
		test.saveReferenceXml();
    }
}