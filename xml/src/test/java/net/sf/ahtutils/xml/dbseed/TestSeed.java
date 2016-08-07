package net.sf.ahtutils.xml.dbseed;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSeed extends AbstractXmlDbseedTest<Seed>
{
	final static Logger logger = LoggerFactory.getLogger(TestSeed.class);
	
	public TestSeed(){super(Seed.class);}
	public static Seed create(boolean withChildren){return (new TestSeed()).build(withChildren);}
	
    public Seed build(boolean withChilds)
    {
    	Seed xml = new Seed();
    	xml.setCode("myCode");
    	xml.setTemplate("myTemplate");
    	xml.setContent("myContent");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestSeed test = new TestSeed();
		test.saveReferenceXml();
    }
}