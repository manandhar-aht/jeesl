package net.sf.ahtutils.xml.sync;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEntity extends AbstractXmlSyncTest<Entity>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEntity.class);
	
	public TestXmlEntity(){super(Entity.class);}
	public static Entity create(boolean withChildren){return (new TestXmlEntity()).build(withChildren);}
    
    public Entity build(boolean withChilds)
    {
    	Entity xml = new Entity();
    	xml.setVersion(0);
    	xml.setType("myType");
    	xml.setValue("123");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlEntity test = new TestXmlEntity();
		test.saveReferenceXml();
    }
}