package org.jeesl.model.xml.jeesl;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.finance.TestXmlCurrency;
import org.jeesl.model.xml.security.TestXmlUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.TestXmlStatus;

public class TestXmlAht extends AbstractXmlJeeslTest<Aht>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAht.class);
	
	public TestXmlAht(){super(Aht.class);}
	public static Aht create(boolean withChildren){return (new TestXmlAht()).build(withChildren);}
    
    public Aht build(boolean withChilds)
    {
    	Aht xml = new Aht();
        	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));xml.getStatus().add(TestXmlStatus.create(false));
    		xml.getCurrency().add(TestXmlCurrency.create(false));xml.getCurrency().add(TestXmlCurrency.create(false));
    		xml.getUser().add(TestXmlUser.create(false));xml.getUser().add(TestXmlUser.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlAht test = new TestXmlAht();
		test.saveReferenceXml();
    }
}