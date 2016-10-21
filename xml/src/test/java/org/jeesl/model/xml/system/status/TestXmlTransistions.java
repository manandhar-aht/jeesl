package org.jeesl.model.xml.system.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Transistions;

public class TestXmlTransistions extends AbstractXmlStatusTest<Transistions>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTransistions.class);
	
	public TestXmlTransistions(){super(Transistions.class);}
	public static Transistions create(boolean withChildren){return (new TestXmlTransistions()).build(withChildren);}   
    
    public Transistions build(boolean withChilds)
    {
    	Transistions xml = new Transistions();
    	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));
    		xml.getStatus().add(TestXmlStatus.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTransistions test = new TestXmlTransistions();
		test.saveReferenceXml();
    }
}