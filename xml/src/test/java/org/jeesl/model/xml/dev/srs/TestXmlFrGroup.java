package org.jeesl.model.xml.dev.srs;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFrGroup extends AbstractXmlSrsTest<FrGroup>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFrGroup.class);
	
	public TestXmlFrGroup(){super(FrGroup.class);}
	public static FrGroup create(boolean withChildren){return (new TestXmlFrGroup()).build(withChildren);}
    
    public FrGroup build(boolean withChildren)
    {
    	FrGroup xml = new FrGroup();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChildren)
    	{
    		xml.getFr().add(TestXmlFr.create(false));
    		xml.getFr().add(TestXmlFr.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlFrGroup test = new TestXmlFrGroup();
		test.saveReferenceXml();
    }
}