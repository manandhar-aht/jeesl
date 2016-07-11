package org.jeesl.model.xml.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlAttribute extends AbstractXmlRevisionTest<Attribute>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAttribute.class);
	
	public TestXmlAttribute(){super(Attribute.class);}
	public static Attribute create(boolean withChildren){return (new TestXmlAttribute()).build(withChildren);} 
    
    public Attribute build(boolean withChilds)
    {
    	Attribute xml = new Attribute();
    	xml.setId(123);
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		
    	}
    	    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlAttribute test = new TestXmlAttribute();
		test.saveReferenceXml();
    }
}