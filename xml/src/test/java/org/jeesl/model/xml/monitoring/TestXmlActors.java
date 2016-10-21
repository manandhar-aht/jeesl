package org.jeesl.model.xml.monitoring;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.monitoring.Actors;

public class TestXmlActors extends AbstractXmlMonitoringTest<Actors>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlActors.class);
	
	public TestXmlActors(){super(Actors.class);}
	public static Actors create(boolean withChildren){return (new TestXmlActors()).build(withChildren);} 
    
    public Actors build(boolean withChilds)
    {
    	Actors xml = new Actors();
    	
    	if(withChilds)
    	{
    		xml.getActor().add(TestXmlActor.create(false));
    		xml.getActor().add(TestXmlActor.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlActors test = new TestXmlActors();
		test.saveReferenceXml();
    }
}