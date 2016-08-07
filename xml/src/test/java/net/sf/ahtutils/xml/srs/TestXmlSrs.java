package net.sf.ahtutils.xml.srs;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSrs extends AbstractXmlSrsTest<Srs>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSrs.class);
	
	public TestXmlSrs(){super(Srs.class);}
	public static Srs create(boolean withChildren){return (new TestXmlSrs()).build(withChildren);}
    
    public Srs build(boolean withChildren)
    {
    	Srs xml = new Srs();
    	xml.setId(123);
    
    	
    	if(withChildren)
    	{
    		xml.getFrGroup().add(TestXmlFrGroup.create(false));
    		xml.getFrGroup().add(TestXmlFrGroup.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlSrs test = new TestXmlSrs();
		test.saveReferenceXml();
    }
}