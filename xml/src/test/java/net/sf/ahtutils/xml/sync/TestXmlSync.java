package net.sf.ahtutils.xml.sync;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlStatus;

public class TestXmlSync extends AbstractXmlSyncTest<Sync>
{
	final static Logger logger = LoggerFactory.getLogger(Sync.class);
	
	public TestXmlSync(){super(Sync.class);}
	public static Sync create(boolean withChildren){return (new TestXmlSync()).build(withChildren);}
	
    public Sync build(boolean withChilds)
    {
    	Sync xml = new Sync();
    	xml.setClientId(123);
    	xml.setServerId(345);
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setResult(net.sf.ahtutils.xml.status.TestXmlResult.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlSync test = new TestXmlSync();
		test.saveReferenceXml();
    }
}