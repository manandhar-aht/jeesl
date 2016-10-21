package org.jeesl.model.xml.jeesl;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.TestXmlStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.aht.Container;

public class TestXmlContainer extends AbstractXmlJeeslTest<Container>
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
		JeeslXmlTestBootstrap.init();
		TestXmlContainer test = new TestXmlContainer();
		test.saveReferenceXml();
    }
}