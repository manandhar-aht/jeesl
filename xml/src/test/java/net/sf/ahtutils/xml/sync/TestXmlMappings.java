package net.sf.ahtutils.xml.sync;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMappings extends AbstractXmlSyncTest<Mappings>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMappings.class);
	
	public TestXmlMappings(){super(Mappings.class);}
	public static Mappings create(boolean withChildren){return (new TestXmlMappings()).build(withChildren);}
    
    public Mappings build(boolean withChilds)
    {
    	Mappings xml = new Mappings();
    	
    	if(withChilds)
    	{
    		xml.getMapper().add(TestXmlMapper.create(false));
    		xml.getMapper().add(TestXmlMapper.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlMappings test = new TestXmlMappings();
		test.saveReferenceXml();
    }
}