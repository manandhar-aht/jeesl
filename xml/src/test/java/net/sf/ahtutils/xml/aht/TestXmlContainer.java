package net.sf.ahtutils.xml.aht;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStatus;

public class TestXmlContainer extends AbstractXmlAhtTest<Container>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlContainer.class);
	
	public TestXmlContainer(){super(Container.class);}
	public static Container create(boolean withChildren){return (new TestXmlContainer()).build(withChildren);}
    
    public Container build(boolean withChilds)
    {
    	Container xml = new Container();
        	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));xml.getStatus().add(TestXmlStatus.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlContainer test = new TestXmlContainer();
		test.saveReferenceXml();
    }
}