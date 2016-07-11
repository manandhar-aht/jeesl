package org.jeesl.model.xml.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlAttribute extends AbstractXmlRevisionTest<Attribute>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAttribute.class);
	
	public TestXmlAttribute(){super(Attribute.class);}
	public static Attribute create(boolean withChildren){return (new TestXmlAttribute()).build(withChildren);} 
    
    public Attribute build(boolean withChilds)
    {
    	Attribute xml = new Attribute();
    	xml.setId(123);
    	xml.setNr(2);
    	xml.setCode("myCode");
    	
    	xml.setXpath("myXPath");
    	xml.setJpa("myJpa");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
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