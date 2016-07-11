package org.jeesl.model.xml.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlEntites extends AbstractXmlRevisionTest<Entities>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEntites.class);
	
	public TestXmlEntites(){super(Entities.class);}
	public static Entities create(boolean withChildren){return (new TestXmlEntites()).build(withChildren);} 
    
    public Entities build(boolean withChilds)
    {
    	Entities xml = new Entities();
    	
    	if(withChilds)
    	{
    		xml.getEntity().add(TestXmlEntity.create(false));
    		xml.getEntity().add(TestXmlEntity.create(false));
    	}
    	    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlEntites test = new TestXmlEntites();
		test.saveReferenceXml();
    }
}