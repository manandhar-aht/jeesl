package org.jeesl.model.xml.system.module.ts;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTimeSeries extends AbstractXmlTimeseriesTest<TimeSeries>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTimeSeries.class);
	
	public TestXmlTimeSeries(){super(TimeSeries.class);}
	public static TimeSeries create(boolean withChildren){return (new TestXmlTimeSeries()).build(withChildren);}
    
    public TimeSeries build(boolean withChilds)
    {
    	TimeSeries xml = new TimeSeries();
    	
    	if(withChilds)
    	{
    		xml.setEntity(TestXmlEntity.create(false));
    		xml.getData().add(TestXmlData.create(false));
    		xml.getData().add(TestXmlData.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTimeSeries test = new TestXmlTimeSeries();
		test.saveReferenceXml();
    }
}